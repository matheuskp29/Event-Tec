package com.event.api.services;

import com.amazonaws.services.s3.AmazonS3;
import com.event.api.domain.entities.Coupon;
import com.event.api.domain.entities.Event;
import com.event.api.domain.exceptions.BusinessException;
import com.event.api.domain.exceptions.GenericException;
import com.event.api.domain.records.dto.CouponDTO;
import com.event.api.domain.records.request.EventRequestDTO;
import com.event.api.domain.records.response.EventDetailsDTO;
import com.event.api.domain.records.response.EventResponseDTO;
import com.event.api.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventService {

    private final AmazonS3 s3Client;
    private final EventRepository eventRepository;
    private final AddressService addressService;
    private final CouponService couponService;

    @Value("${aws.bucket.name}")
    private String bucketName;

    /**
     * Create an Event
     * @param data request data
     */
    public void createEvent(EventRequestDTO data) {
        try {
            String imgUrl = null;

            if (Objects.nonNull(data.image())) {
                imgUrl = this.uploadImg(data.image());
            }

            Event event = eventRepository.save(Event.builder()
                    .title(data.title())
                    .description(data.description())
                    .eventUrl(data.eventUrl())
                    .eventDate(new Date(data.eventDate()))
                    .remote(data.remote())
                    .imgUrl(imgUrl)
                    .build());

            if (!data.remote()) {
                this.addressService.createAddress(data, event);
            }

        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            throw new GenericException("Error creating event: " + e.getMessage(), e);
        }
    }

    /**
     * uploadImg in AWS
     * @param multipartFile multipartfile
     * @return URL img uploaded
     */
    private String uploadImg(MultipartFile multipartFile) {
        String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        
        try {
            File file = this.convertMultipartToFile(multipartFile);
            s3Client.putObject(bucketName, filename, file);
            file.delete();
            return s3Client.getUrl(bucketName, filename).toString();
        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            throw new GenericException("Error uploading image to S3 bucket: " + e.getMessage(), e);
        }
    }

    /**
     * Convert multipartFile
     * @param multipartFile file
     * @return File
     * @throws IOException Converting error
     */
    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file);){
            fos.write(multipartFile.getBytes());
            return file;
        } catch (IOException e) {
            throw new GenericException("Error converting multipart file to file: " + e.getMessage(), e);
        }
    }

    /**
     * get all events
     * @param page page
     * @param size page size
     * @return List of events
     */
    public List<EventResponseDTO> getUpComingEvents(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Event> eventsPage = eventRepository.findUpComingEvents(new Date(), pageable);

            return getMapEvents(eventsPage);
        } catch (Exception e) {
            throw new GenericException("Error to get events: " + e.getMessage(), e);
        }
    }

    /**
     * get filtered events
     * @param page page
     * @param size page size
     * @param title title of event
     * @param city city of events
     * @param uf state of events
     * @param startDate start date
     * @param endDate end date
     * @return Filtered events
     */
    public List<EventResponseDTO> getFilteredEvents(int page, int size, String title, String city, String uf, Date startDate, Date endDate) {
        try {
            title = Objects.nonNull(title) ? title : "";
            city = Objects.nonNull(city) ? city : "";
            uf = Objects.nonNull(uf) ? uf : "";
            startDate = Objects.nonNull(startDate) ? startDate : new Date();
            endDate = Objects.nonNull(endDate) ? endDate : new Date(32489908339000L);

            Pageable pageable = PageRequest.of(page, size);
            Page<Event> eventsPage = eventRepository.findFilteredEvents(title, city, uf, startDate, endDate, pageable);

            return getMapEvents(eventsPage);
        } catch (Exception e) {
            throw new GenericException("Error to get events: " + e.getMessage(), e);
        }
    }

    /**
     * Map events
     * @param eventPage event page
     * @return
     */
    private List<EventResponseDTO> getMapEvents(Page<Event> eventPage) {
        return eventPage
                .map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getEventDate(),
                        Objects.nonNull(event.getAddress()) ? event.getAddress().getCity() : "",
                        Objects.nonNull(event.getAddress()) ? event.getAddress().getUf() : "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl()))
                .toList();
    }

    /**
     * Get event details
     * @param eventId event id
     * @return Details of event
     */
    public EventDetailsDTO getEventDetails(UUID eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new BusinessException("Event not found"));

        List<Coupon> coupons = couponService.consultCoupons(event, new Date());

        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getEventDate(),
                Objects.nonNull(event.getAddress()) ? event.getAddress().getCity() : "",
                Objects.nonNull(event.getAddress()) ? event.getAddress().getUf() : "",
                event.getImgUrl(),
                event.getEventUrl(),
                coupons.stream().map(coupon -> new CouponDTO(coupon.getCode(), coupon.getDiscount(), coupon.getValid())).toList()
        );
    }
}

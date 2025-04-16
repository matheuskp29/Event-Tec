package com.event.api.services;

import com.amazonaws.services.s3.AmazonS3;
import com.event.api.domain.entities.Event;
import com.event.api.domain.exceptions.GenericException;
import com.event.api.domain.records.EventRequestDTO;
import com.event.api.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class EventService {

    private final AmazonS3 s3Client;
    private final EventRepository eventRepository;

    @Value("${aws.bucket.name}")
    private String bucketName;

    public Event createEvent(EventRequestDTO data) {
        try {
            String imgUrl = null;

            if (Objects.nonNull(data.image())) {
                imgUrl = this.uploadImg(data.image());
            }

            return eventRepository.save(Event.builder()
                    .title(data.title())
                    .description(data.description())
                    .eventUrl(data.eventUrl())
                    .eventDate(new Date(data.eventDate()))
                    .remote(data.remote())
                    .imgUrl(imgUrl)
                    .build());
        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            throw new GenericException("Error creating event: " + e.getMessage(), e);
        }
    }

    private String uploadImg(MultipartFile multipartFile) {
        String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        
        try {
            File file = this.convertMultipartToFile(multipartFile);
            s3Client.putObject(bucketName, filename, file);
            file.delete();
            return s3Client.getUrl(bucketName, filename).toString();
        } catch (GenericException e) {
            log.error("Error converting multipart file to file: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error uploading image to S3 bucket: {}", e.getMessage());
        }
        return "";
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
}

package com.event.api.services;

import com.event.api.domain.entities.Coupon;
import com.event.api.domain.entities.Event;
import com.event.api.domain.exceptions.BusinessException;
import com.event.api.domain.exceptions.GenericException;
import com.event.api.domain.records.request.CouponRequestDTO;
import com.event.api.domain.records.response.CouponResponseDTO;
import com.event.api.repositories.CouponRepository;
import com.event.api.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {

    private final CouponRepository couponRepository;
    private final EventRepository eventRepository;

    /**
     * Add coupon to Event
     * @param eventId eventId
     * @param data Request data
     * @return persisted coupon
     */
    public CouponResponseDTO addCouponToEvent(UUID eventId, CouponRequestDTO data) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new BusinessException("Event not found by event id: " + eventId));
        try {
            Coupon coupon = Coupon.builder()
                    .code(data.code())
                    .discount(data.discount())
                    .valid(new Date(data.valid()))
                    .event(event)
                    .build();

            couponRepository.save(coupon);

            return new CouponResponseDTO(data.code(), data.discount(), coupon.getValid(), event.getTitle(), event.getDescription());
        } catch (Exception e) {
            throw new GenericException("Error to add coupon: " + e.getMessage(), e);
        }
    }

    /**
     * Coupons by event id
     * @param event event
     * @param currentDate Current date for valid validation
     * @return List of coupons by event
     */
    public List<Coupon> consultCoupons(Event event, Date currentDate) {
        try {
            return couponRepository.findByEventAndValidAfter(event, currentDate);
        } catch (Exception e) {
            throw new GenericException("Unexpected error in found coupons: " + e.getMessage(), e);
        }
    }
}

package com.event.api.controllers;

import com.event.api.domain.records.request.CouponRequestDTO;
import com.event.api.domain.records.response.CouponResponseDTO;
import com.event.api.services.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController extends BaseController {

    private final CouponService couponService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<CouponResponseDTO> addCouponsToEvent(@PathVariable UUID eventId, @RequestBody CouponRequestDTO data) {
        return ResponseEntity.ok(couponService.addCouponToEvent(eventId, data));
    }
}

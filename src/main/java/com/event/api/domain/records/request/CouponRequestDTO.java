package com.event.api.domain.records.request;

public record CouponRequestDTO(String code, Integer discount, Long valid) {
}

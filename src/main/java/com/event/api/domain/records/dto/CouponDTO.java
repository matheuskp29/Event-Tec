package com.event.api.domain.records.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public record CouponDTO(
        @Schema(name = "code", description = "Code of coupon") String code,
        @Schema(name = "discount", description = "Discount of coupon") Integer discount,
        @Schema(name = "validUntil", description = "Valid of coupon") Date validUntil) {
}

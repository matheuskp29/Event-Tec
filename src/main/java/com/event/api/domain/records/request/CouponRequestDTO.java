package com.event.api.domain.records.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CouponRequestDTO(

        @Schema(name = "code", description = "code of coupon", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "don't permited null value in 'code'")
        String code,

        @Schema(name = "discount", description = "Discount of coupon", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "don't permited null value in 'discount'")
        Integer discount,

        @Schema(name = "valid", description = "Valid of coupon", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Don't permited null value in 'valid'")
        Long valid) {
}

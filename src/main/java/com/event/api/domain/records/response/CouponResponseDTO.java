package com.event.api.domain.records.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CouponResponseDTO(
        @Schema(name = "code", description = "code of coupon") String code,
        @Schema(name = "discount", description = "Discount of coupon") Integer discount,
        @Schema(name = "valid", description = "Valid of coupon") Date valid,
        @Schema(name = "eventTitle", description = "Title of event") String eventTitle,
        @Schema(name = "eventDescription", description = "Description of event") String eventDescription) {
}

package com.event.api.domain.records.response;

import com.event.api.domain.records.dto.CouponDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EventDetailsDTO(
        @Schema(name = "id", description = "Id of event", type = "uuid", example = "39187b41-3ccc-4b85-925a-7e310d5badb9") UUID id,
        @Schema(name = "title", description = "Title of event") String title,
        @Schema(name = "description", description = "Description of event", type = "string") String description,
        @Schema(name = "eventDate", description = "Date of event") Date eventDate,
        @Schema(name = "city", description = "City of event") String city,
        @Schema(name = "uf", description = "State of event") String uf,
        @Schema(name = "imgUrl", description = "URL of image") String imgUrl,
        @Schema(name = "eventUrl", description = "URL of event") String eventUrl,
        @Schema(name = "coupons", description = "List of coupons", implementation = CouponDTO[].class) List<CouponDTO> coupons) {
}

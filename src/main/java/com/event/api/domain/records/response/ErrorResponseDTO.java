package com.event.api.domain.records.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ErrorResponseDTO(
        @Schema(name = "message", description = "Error message") String message,
        @Schema(name = "statusCode", description = "Status code error") Integer statusCode) {
}

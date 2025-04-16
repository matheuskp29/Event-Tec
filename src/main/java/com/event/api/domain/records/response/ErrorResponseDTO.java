package com.event.api.domain.records.response;

import lombok.Builder;

@Builder
public record ErrorResponseDTO(String message, Integer statusCode) {
}

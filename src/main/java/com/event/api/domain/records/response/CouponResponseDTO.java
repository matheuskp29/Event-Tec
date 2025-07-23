package com.event.api.domain.records.response;

import java.time.LocalDate;

public record CouponResponseDTO(String code, Integer discount, LocalDate valid, String eventTitle, String eventDescription) {
}

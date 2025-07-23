package com.event.api.domain.records.response;

import java.util.Date;

public record CouponResponseDTO(String code, Integer discount, Date valid, String eventTitle, String eventDescription) {
}

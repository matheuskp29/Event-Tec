package com.event.api.domain.records.response;

import java.util.Date;
import java.util.UUID;

public record EventResponseDTO(UUID id, String title, String description, Date eventDate, String city, String state,
                               Boolean remote, String eventUrl, String imgUrl) {
}

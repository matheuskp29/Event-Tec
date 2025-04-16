package com.event.api.domain.records;

import org.springframework.web.multipart.MultipartFile;

public record EventRequestDTO(String title, String description, Long eventDate, String city, String state,
                              Boolean remote, String eventUrl, MultipartFile image) {
}

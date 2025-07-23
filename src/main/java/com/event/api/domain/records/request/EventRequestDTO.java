package com.event.api.domain.records.request;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.multipart.MultipartFile;

@Hidden
public record EventRequestDTO(String title, String description, Long eventDate, String city, String state,
                              Boolean remote, String eventUrl, MultipartFile image) {
}

package com.event.api.domain.records.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.UUID;

public record EventResponseDTO(

        @Schema(name = "id", description = "Id of event") UUID id,
        @Schema(name = "title", description = "Title of event") String title,
        @Schema(name = "description", description = "Description of event") String description,
        @Schema(name = "eventDate", description = "Date of event") Date eventDate,
        @Schema(name = "city", description = "City of event") String city,
        @Schema(name = "state", description = "State of event") String state,
        @Schema(name = "imgUrl", description = "URL of image") String imgUrl,
        @Schema(name = "eventUrl", description = "URL of event") String eventUrl,
        @Schema(name = "remote", description = "Remote") Boolean remote) { }

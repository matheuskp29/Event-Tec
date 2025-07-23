package com.event.api.controllers;

import com.event.api.domain.records.request.EventRequestDTO;
import com.event.api.domain.records.response.EventResponseDTO;
import com.event.api.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
@Tag(name = "Event", description = "Event operations")
public class EventController extends BaseController {

    private final EventService eventService;

    @Operation(
            summary = "Create Event", description = "Creating a event", parameters = {
                @Parameter(name = "title", description = "event title", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string")),
                @Parameter(name = "description", description = "event description", in = ParameterIn.PATH, schema = @Schema(type = "string")),
                @Parameter(name = "eventDate", description = "event date", required = true, in = ParameterIn.PATH, schema = @Schema(type = "long")),
                @Parameter(name = "city", description = "event city", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string")),
                @Parameter(name = "state", description = "event state", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string")),
                @Parameter(name = "remote", description = "remote", required = true, in = ParameterIn.PATH, schema = @Schema(type = "boolean")),
                @Parameter(name = "eventUrl", description = "Url of event", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string")),
                @Parameter(name = "image", description = "Image", in = ParameterIn.PATH, schema = @Schema(type = "multipartFile"))
            },
            responses = {
                @ApiResponse(responseCode = "201", description = "Event created"),
                @ApiResponse(responseCode = "500", description = "Unexpected error")
            }
    )
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Void> create(@RequestParam("title") String title,
                                        @RequestParam(value = "description", required = false) String description,
                                        @RequestParam("eventDate") Long eventDate,
                                        @RequestParam("city") String city,
                                        @RequestParam("state") String state,
                                        @RequestParam("remote") Boolean remote,
                                        @RequestParam("eventUrl") String eventUrl,
                                        @RequestParam(value = "image", required = false) MultipartFile image) {
        EventRequestDTO body = new EventRequestDTO(title, description, eventDate, city, state, remote, eventUrl, image);
        eventService.createEvent(body);

        return super.created();
    }

    @Operation(
            summary = "Get events", description = "Get all events", parameters = {
            @Parameter(name = "page", description = "page", in = ParameterIn.PATH, schema = @Schema(type = "integer", defaultValue = "0")),
            @Parameter(name = "size", description = "page size", in = ParameterIn.PATH, schema = @Schema(type = "integer", defaultValue = "10")),
    },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of events", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDTO[].class))),
                    @ApiResponse(responseCode = "400", description = "Business error", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    public ResponseEntity<List<EventResponseDTO>> getEvents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(eventService.getEvents(page, size));
    }
}

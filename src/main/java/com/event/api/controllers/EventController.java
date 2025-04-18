package com.event.api.controllers;

import com.event.api.domain.entities.Event;
import com.event.api.domain.records.EventRequestDTO;
import com.event.api.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Event> create(@RequestParam("title") String title,
                                        @RequestParam(value = "description", required = false) String description,
                                        @RequestParam("eventDate") Long eventDate,
                                        @RequestParam("city") String city,
                                        @RequestParam("state") String state,
                                        @RequestParam("remote") Boolean remote,
                                        @RequestParam("eventUrl") String eventUrl,
                                        @RequestParam(value = "image", required = false)MultipartFile image) {
        EventRequestDTO body = new EventRequestDTO(title, description, eventDate, city, state, remote, eventUrl, image);
        return ResponseEntity.ok(eventService.createEvent(body));
    }
}

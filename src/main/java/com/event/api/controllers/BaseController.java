package com.event.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    public ResponseEntity<Void> created() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

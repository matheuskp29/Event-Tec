package com.event.api.controllers;

import com.event.api.domain.exceptions.BusinessException;
import com.event.api.domain.exceptions.GenericException;
import com.event.api.domain.records.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ErrorResponseDTO> handler(final BusinessException exception) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .message(exception.getMessage())
                        .statusCode(400)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({GenericException.class})
    public ResponseEntity<ErrorResponseDTO> handler(final GenericException exception) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                .message(exception.getMessage())
                .statusCode(500)
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponseDTO> handler(final Exception exception) {
        return new ResponseEntity<>(
                ErrorResponseDTO.builder()
                        .message(exception.getMessage())
                        .statusCode(500)
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

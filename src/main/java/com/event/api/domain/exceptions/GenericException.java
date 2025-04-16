package com.event.api.domain.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GenericException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }
}

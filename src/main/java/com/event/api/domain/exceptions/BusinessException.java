package com.event.api.domain.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }
}

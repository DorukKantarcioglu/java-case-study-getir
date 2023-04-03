package com.getir.readingisgood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InsufficientEntityException extends RuntimeException {
    public InsufficientEntityException(String message) {
        super(message);
    }
}

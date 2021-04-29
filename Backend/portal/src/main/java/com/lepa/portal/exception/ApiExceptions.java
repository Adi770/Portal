package com.lepa.portal.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ApiExceptions {

    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;

    public ApiExceptions(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}

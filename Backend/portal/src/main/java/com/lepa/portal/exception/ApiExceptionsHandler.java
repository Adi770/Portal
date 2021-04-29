package com.lepa.portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionsHandler {
    HttpStatus httpStatus;

    @ExceptionHandler(value = {UserNotFoundException.class,NewsNotFoundException.class,TopicNotFoundException.class})
    public ResponseEntity<Object> notFoundHandlerExceptions(RuntimeException e) {
        httpStatus = HttpStatus.NOT_FOUND;
        ApiExceptions apiExceptions = new ApiExceptions(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.systemDefault())
        );
        return new ResponseEntity<>(apiExceptions, httpStatus);
    }

    @ExceptionHandler(value = {WrongRoleException.class,WrongUserException.class})
    public ResponseEntity<Object> wrongDataHandlerExceptions(RuntimeException e) {
        httpStatus = HttpStatus.BAD_REQUEST;
        ApiExceptions apiExceptions = new ApiExceptions(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiExceptions,httpStatus);
    }


}

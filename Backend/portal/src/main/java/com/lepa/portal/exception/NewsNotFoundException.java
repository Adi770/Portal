package com.lepa.portal.exception;

public class NewsNotFoundException extends RuntimeException{
    public NewsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewsNotFoundException(String message) {
        super(message);
    }
}

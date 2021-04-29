package com.lepa.portal.exception;

public class CommentsNotFoundException extends RuntimeException {
    public CommentsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentsNotFoundException(String message) {
        super(message);
    }
}

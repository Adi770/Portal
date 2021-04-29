package com.lepa.portal.exception;

public class TopicNotFoundException extends RuntimeException{
    public TopicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TopicNotFoundException(String message) {
        super(message);
    }
}

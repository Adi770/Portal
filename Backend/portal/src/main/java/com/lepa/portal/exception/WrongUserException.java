package com.lepa.portal.exception;

public class WrongUserException extends RuntimeException{
    public WrongUserException(String message, Throwable cause) {
        super(message, cause);
    }
}

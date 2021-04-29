package com.lepa.portal.exception;

public class WrongRoleException extends SecurityException{
    public WrongRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongRoleException(String message) {
        super(message);
    }
}

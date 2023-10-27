package com.myproject.library.Exceptions;

public class EmailUsedException extends Exception{
    public EmailUsedException() {
        super();
    }


    public EmailUsedException(String message) {
        super(message);
    }


    public EmailUsedException(String message, Throwable cause) {
        super(message, cause);
    }
}

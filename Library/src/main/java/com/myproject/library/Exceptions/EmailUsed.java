package com.myproject.library.Exceptions;

public class EmailUsed extends Exception{
    public EmailUsed() {
        super();
    }


    public EmailUsed(String message) {
        super(message);
    }


    public EmailUsed(String message, Throwable cause) {
        super(message, cause);
    }
}

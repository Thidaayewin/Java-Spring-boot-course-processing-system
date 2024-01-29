package com.team3.caps.exception;

public class InvalidTokenException extends Exception {
    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String messsage) {
        super(messsage);
    }

}
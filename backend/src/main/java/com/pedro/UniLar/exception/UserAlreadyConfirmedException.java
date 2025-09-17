package com.pedro.UniLar.exception;


public class UserAlreadyConfirmedException extends RuntimeException {

    public UserAlreadyConfirmedException(String message) {
        super(message);
    }
}

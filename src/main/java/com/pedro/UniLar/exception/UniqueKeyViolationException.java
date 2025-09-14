package com.pedro.UniLar.exception;

public class UniqueKeyViolationException extends RuntimeException {
    public UniqueKeyViolationException(String message) {
        super(message);
    }
}

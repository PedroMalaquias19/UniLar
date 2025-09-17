package com.pedro.UniLar.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime zonedDateTime) {
    @Override
    @JsonIgnore
    public Throwable throwable() {
        return throwable;
    }
}
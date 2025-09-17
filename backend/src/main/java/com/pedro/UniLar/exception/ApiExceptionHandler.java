package com.pedro.UniLar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e){

        ApiException apiException = new ApiException(e.getMessage(), e, HttpStatus.NOT_FOUND, ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e){

        ApiException apiException = new ApiException(e.getMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = NotAllowedException.class)
    public ResponseEntity<Object> handleNotAllowedException(NotAllowedException e){

        ApiException apiException = new ApiException(e.getMessage(), e, HttpStatus.NOT_ACCEPTABLE, ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.NOT_ACCEPTABLE);

    }

    @ExceptionHandler(value = UserAlreadyConfirmedException.class)
    public ResponseEntity<Object> handleUserAlreadyConfirmedException(UserAlreadyConfirmedException e){

        ApiException apiException = new ApiException(e.getMessage(), e, HttpStatus.SEE_OTHER, ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.SEE_OTHER);

    }

    @ExceptionHandler(value = EmailSendingException.class)
    public ResponseEntity<Object> handleEmailSendingException(EmailSendingException e){

        ApiException apiException = new ApiException(e.getMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UniqueKeyViolationException.class)
    public ResponseEntity<Object> handleUniqueKeyViolationException(UniqueKeyViolationException e){

        ApiException apiException = new ApiException(e.getMessage(), e, HttpStatus.CONFLICT, ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = LimitExceededException.class)
    public ResponseEntity<Object> handleLimitExceededException(LimitExceededException e){

        ApiException apiException = new ApiException(e.getMessage(), e, HttpStatus.FORBIDDEN, ZonedDateTime.now());

        return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);
    }

    //TODO. Handle this exception -> No enum constant achama.website.donation.enums.DonationStatus.ACCEPTED

}

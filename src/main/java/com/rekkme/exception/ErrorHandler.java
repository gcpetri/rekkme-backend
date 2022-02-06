package com.rekkme.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RecordNotFoundException handleRecordNotFoundException(RecordNotFoundException ce) {
        return ce;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UserNotFoundException handleUserNotFoundException(UserNotFoundException ce) {
        return ce;
    }

    @ExceptionHandler(CreateUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CreateUserException handleCreateUserException(CreateUserException ce) {
        return ce;
    }

    @ExceptionHandler(RedirectToCreateNewPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RedirectToCreateNewPasswordException handleRedirectToCreateNewPasswordException(RedirectToCreateNewPasswordException ce) {
        return ce;
    }

    @ExceptionHandler(RedirectToLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RedirectToLoginException handleRedirectToLoginException(RedirectToLoginException ce) {
        return ce;
    }
}

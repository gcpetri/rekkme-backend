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

    @ExceptionHandler(FriendException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FriendException handleFriendException(FriendException ce) {
        return ce;
    }

    @ExceptionHandler(ResultAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultAlreadyExistsException handleResultAlreadyExistsException(ResultAlreadyExistsException ce) {
        return ce;
    }

    @ExceptionHandler(NotEnoughPointsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public NotEnoughPointsException handleNotEnoughPointsException(NotEnoughPointsException ce) {
        return ce;
    }
}

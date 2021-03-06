package com.rekkme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Create User Error") 
public class CreateUserException extends RuntimeException {
    public CreateUserException(String reason) {
        super("Could not create user: " + reason);
    }
}
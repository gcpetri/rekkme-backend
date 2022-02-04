package com.rekkme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="No such record") 
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String entityName, String username) {
        super("Could not find record of " + entityName + " with username: " + username);
    }
}
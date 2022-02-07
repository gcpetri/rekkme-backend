package com.rekkme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Friend Error") 
public class FriendException extends RuntimeException {
    public FriendException(String reason) {
        super("Invalid Friend Request: " + reason);
    }
}

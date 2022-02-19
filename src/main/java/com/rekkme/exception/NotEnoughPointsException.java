package com.rekkme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Not Enough Points") 
public class NotEnoughPointsException extends RuntimeException {
    public NotEnoughPointsException(int currentPoints) {
        super("You cannot wager over " + Integer.toString(currentPoints) + " points");
    }
}
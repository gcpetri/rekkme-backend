package com.rekkme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Result Already Recorded") 
public class ResultAlreadyExistsException extends RuntimeException {
    public ResultAlreadyExistsException() {
        super("This recommendation already has a result recorded.");
    }
}

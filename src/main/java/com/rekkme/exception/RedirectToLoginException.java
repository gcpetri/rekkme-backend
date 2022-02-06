package com.rekkme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Login Required") 
public class RedirectToLoginException extends RuntimeException {
    public RedirectToLoginException() {
        super("Bearer Token Not Found");
    }
}

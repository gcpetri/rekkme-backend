package com.rekkme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Create New Password") 
public class RedirectToCreateNewPasswordException extends RuntimeException {
    public RedirectToCreateNewPasswordException() {
        super("Password has expired");
    }
}

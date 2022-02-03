package com.rekkme.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="No such record") 
public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String entityName, UUID id) {
        super("Could not find record of " + entityName + " with id " + id.toString());
    }
}
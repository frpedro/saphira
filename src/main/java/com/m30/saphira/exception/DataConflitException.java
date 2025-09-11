package com.m30.saphira.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataConflitException extends RuntimeException {

    public DataConflitException(String message) {
        super(message);
    }

}


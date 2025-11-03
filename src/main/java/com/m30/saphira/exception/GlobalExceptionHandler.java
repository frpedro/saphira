package com.m30.saphira.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
@Slf4j

public class GlobalExceptionHandler {

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<Map<String, Object>> handleDataConflict(DataConflictException ex) {

        log.error("Data conflict {}", ex.getMessage());
        Map<String,Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status",409 ,
                "error", "Data conflict",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {

        log.error("Not found {}",ex.getMessage());
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 404,
                "error", "Not found",
                "message", ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 500,
                "error", "Internal Server Error",
                "message", "An unexpected error occurred"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}

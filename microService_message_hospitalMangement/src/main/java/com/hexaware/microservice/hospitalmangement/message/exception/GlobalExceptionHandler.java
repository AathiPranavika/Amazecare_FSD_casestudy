package com.hexaware.microservice.hospitalmangement.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
	@ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<String> MessageNotFound(MessageNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	 @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
	        return new ResponseEntity<>("Runtime Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}

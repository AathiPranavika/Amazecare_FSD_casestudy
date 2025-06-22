package com.hexaware.hospitalmanagement.exception;
/**
 * Custom exception to be thrown when an Admin entity is not found in the system.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}

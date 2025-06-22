package com.hexaware.hospitalmanagement.exception;

/**
 * Custom exception to be thrown when user is not found in the system.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

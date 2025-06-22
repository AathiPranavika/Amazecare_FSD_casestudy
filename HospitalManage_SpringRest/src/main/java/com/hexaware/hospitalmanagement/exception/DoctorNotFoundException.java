package com.hexaware.hospitalmanagement.exception;
/**
 * Custom exception to be thrown when doctor entity is not found in the system.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
public class DoctorNotFoundException extends RuntimeException {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}

package com.hexaware.hospitalmanagement.exception;

/**
 * Custom exception to be thrown when patient  is not found in the system.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}

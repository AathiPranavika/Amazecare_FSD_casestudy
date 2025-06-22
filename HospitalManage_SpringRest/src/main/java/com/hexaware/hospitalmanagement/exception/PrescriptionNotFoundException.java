package com.hexaware.hospitalmanagement.exception;

/**
 * Custom exception to be thrown when prescription  is not found in the system.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
public class PrescriptionNotFoundException extends Exception {
    public PrescriptionNotFoundException(String message) {
        super(message);
    }
}

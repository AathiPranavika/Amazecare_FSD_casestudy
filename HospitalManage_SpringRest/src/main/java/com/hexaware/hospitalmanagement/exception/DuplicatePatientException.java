package com.hexaware.hospitalmanagement.exception;
/**
 * Custom exception to be thrown when duplicate patient is found in the system.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */

public class DuplicatePatientException extends Exception {
    public DuplicatePatientException(String message) {
        super(message);
    }
}

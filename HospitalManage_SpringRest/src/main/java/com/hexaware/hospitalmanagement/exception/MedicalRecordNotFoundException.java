package com.hexaware.hospitalmanagement.exception;

/**
 * Custom exception to be thrown when medical record  is not found in the system.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
public class MedicalRecordNotFoundException extends Exception {

    public MedicalRecordNotFoundException(String message) {
        super(message);
    }

    
}

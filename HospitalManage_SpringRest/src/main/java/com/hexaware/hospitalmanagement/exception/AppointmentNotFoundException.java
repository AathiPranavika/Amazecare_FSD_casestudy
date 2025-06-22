package com.hexaware.hospitalmanagement.exception;
/**
 * Custom exception to be thrown when an appointment entity is not found in the system.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
public class AppointmentNotFoundException extends Exception {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}

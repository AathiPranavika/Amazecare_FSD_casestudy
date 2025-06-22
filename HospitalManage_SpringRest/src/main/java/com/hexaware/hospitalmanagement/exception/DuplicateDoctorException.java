package com.hexaware.hospitalmanagement.exception;
/**
 * Custom exception to be thrown when duplicate doctor is found in the system.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
public class DuplicateDoctorException extends Exception {
    public DuplicateDoctorException(String message) {
        super(message);
    }
}

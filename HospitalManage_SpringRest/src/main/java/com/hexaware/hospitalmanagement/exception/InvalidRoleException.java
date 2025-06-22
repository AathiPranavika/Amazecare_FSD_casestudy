package com.hexaware.hospitalmanagement.exception;
/**
 * Custom exception to be thrown when invalid role is found in the system.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
public class InvalidRoleException extends Exception {
    public InvalidRoleException(String message) {
        super(message);
    }
}

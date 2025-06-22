package com.hexaware.hospitalmanagement.DTO;


/**
 * PatientDTO is a data carrier class for transferring Patient details with validation.
 * @author Aathi Pranavika
 * @version 1.0
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PatientDTO {

    private Long userId;

    private String address;

    @NotBlank(message = "Emergency contact is required")
    @Pattern(regexp = "^\\d{10}$", message = "Emergency contact must be a valid phone number")
    private String emergencyContact;

    @NotBlank(message = "Blood group is required")
    @Pattern(
        regexp = "^(A|B|AB|O)[+-]$",
        message = "Blood group must be one of: A+, A-, B+, B-, O+, O-, AB+, AB-"
    )
    private String bloodGroup;

    private String medicalHistory;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String toString() {
        return "PatientDTO [userId=" + userId + ", address=" + address + ", emergencyContact=" + emergencyContact
                + ", bloodGroup=" + bloodGroup + ", medicalHistory=" + medicalHistory + "]";
    }
}

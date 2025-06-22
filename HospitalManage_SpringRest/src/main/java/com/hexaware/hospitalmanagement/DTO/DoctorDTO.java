package com.hexaware.hospitalmanagement.DTO;

/**
 * DoctorDTO is a data carrier class for transferring Doctor details with validation.
 * @author Aathi Pranavika
 * @version 1.0
 */
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class DoctorDTO {

    private Long doctorId;

    private Long userId; 

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @Min(value = 0, message = "Experience years must be zero or positive")
    private int experienceYears;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @NotBlank(message = "Designation is required")
    private String designation;

    public DoctorDTO() {}

    public DoctorDTO(Long doctorId, Long userId, String specialization, int experienceYears, String qualification, String designation) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.specialization = specialization;
        this.experienceYears = experienceYears;
        this.qualification = qualification;
        this.designation = designation;
    }

    // Getters and Setters
    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "DoctorDTO [doctorId=" + doctorId + ", userId=" + userId + ", specialization=" + specialization
                + ", experienceYears=" + experienceYears + ", qualification=" + qualification + ", designation="
                + designation + "]";
    }
}

package com.hexaware.hospitalmanagement.DTO;
/**
 * AdminDTO is a data carrier class for transferring Admin details with validation.
 * @author Aathi Pranavika
 * @version 1.0
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class AdminDTO {

    @NotBlank(message = "Admin code is required")
    @Pattern(regexp = "ADM\\d{3}", message = "Admin code must follow pattern ADMxxx (e.g., ADM001)")
    private String adminCode;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    private Long userId;

    // --- Getters and Setters ---

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AdminDTO [adminCode=" + adminCode + ", department=" + department +
               ", qualification=" + qualification + ", userId=" + userId + "]";
    }
}

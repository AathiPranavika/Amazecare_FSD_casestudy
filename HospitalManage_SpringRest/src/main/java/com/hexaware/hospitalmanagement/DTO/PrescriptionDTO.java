package com.hexaware.hospitalmanagement.DTO;


/**
 * PrescriptionDTO is a data carrier class for transferring Prescription details with validation.
 * @author Aathi Pranavika
 * @version 1.0
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PrescriptionDTO {

	private Long prescriptionId;
	
    @NotNull(message = "Medical record ID must be provided")
    private Long medicalRecordId;

    @NotBlank(message = "Medicine name is required")
    @Size(max = 50, message = "Medicine name must be at most 50 characters")
    private String medicineName;

    @Size(max = 20, message = "Dosage must be at most 20 characters")
    private String dosage;

    @Size(max = 2000, message = "Remarks must be at most 2000 characters")
    private String remarks;

    // Getters and Setters

    
    public Long getMedicalRecordId() {
        return medicalRecordId;
    }

    public Long getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public void setMedicalRecordId(Long medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "PrescriptionDTO [medicalRecordId=" + medicalRecordId + ", medicineName=" + medicineName
                + ", dosage=" + dosage + ", remarks=" + remarks + "]";
    }
}

package com.hexaware.hospitalmanagement.entity;
/**
 * Entity class representing Prescription in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "recordId", nullable = false,
                foreignKey = @ForeignKey(name = "fk_prescription_record"))
    @NotNull(message = "Medical record must be specified")
    private MedicalRecord medicalRecord;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "Medicine name is required")
    @Size(max = 50, message = "Medicine name must be at most 50 characters")
    private String medicineName;

    @Column(length = 20)
    @Size(max = 20, message = "Dosage must be at most 20 characters")
    private String dosage;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000, message = "Remarks must be at most 2000 characters")
    private String remarks;


    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
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
		return "Prescription [prescriptionId=" + prescriptionId + ", medicalRecord=" + medicalRecord + ", medicineName="
				+ medicineName + ", dosage=" + dosage + ", remarks=" + remarks + "]";
	}
    
    
}

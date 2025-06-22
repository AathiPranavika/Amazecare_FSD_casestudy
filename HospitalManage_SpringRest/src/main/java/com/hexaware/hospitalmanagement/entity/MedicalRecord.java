package com.hexaware.hospitalmanagement.entity;
/**
 * Entity class representing an MedicalRecord in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Table(name="MedicalRecords")
@Entity
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @OneToOne
    @JoinColumn(name = "appointmentId", nullable = false,
                foreignKey = @ForeignKey(name = "fk_medical_record_appointment"))
    @NotNull(message = "Appointment must be specified")
    @JsonBackReference
    private Appointment appointment;

    @Column(columnDefinition = "TEXT")
    private String symptoms;

    @Column(columnDefinition = "TEXT")
    private String physicalExam;

    @Column(columnDefinition = "TEXT",nullable=false)
    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String treatmentPlan;

    @CreationTimestamp
    @Column(updatable = false, insertable = true)

    private LocalDateTime createdAt;

    //when a medical record deleted all related prescription will be deleted
    @OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Prescription> prescriptions = new HashSet<>();


    // Getters and Setters

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPhysicalExam() {
        return physicalExam;
    }

    public void setPhysicalExam(String physicalExam) {
        this.physicalExam = physicalExam;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

	public Set<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	@Override
	public String toString() {
		return "MedicalRecord [recordId=" + recordId + ", appointment=" + appointment + ", symptoms=" + symptoms
				+ ", physicalExam=" + physicalExam + ", diagnosis=" + diagnosis + ", treatmentPlan=" + treatmentPlan
				+ ", createdAt=" + createdAt + "]";
	}
    
}

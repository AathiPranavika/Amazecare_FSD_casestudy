package com.hexaware.hospitalmanagement.entity;

/**
 * Entity class representing an Appointment in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    @JsonIgnoreProperties("appointments") 
    @JoinColumn(name = "patientId", nullable = false,
                foreignKey = @ForeignKey(name = "fk_appointment_patient"))
    @NotNull(message = "Patient must be specified")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("appointments") 
    @JoinColumn(name = "doctorId", nullable = false,
                foreignKey = @ForeignKey(name = "fk_appointment_doctor"))
    @NotNull(message = "Doctor must be specified")
    private Doctor doctor;

    @Column(nullable = true)
    //@NotNull(message = "Appointment date and time is required")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDateTime appointmentDate;

    @Column(columnDefinition = "TEXT")
    private String symptoms;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'REJECTED', 'COMPLETED') DEFAULT 'PENDING'")
    @NotNull(message = "Status must be specified")
    private AppointmentStatus status = AppointmentStatus.PENDING;

    
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private MedicalRecord medicalRecord;

    @CreationTimestamp //@UpdateTimestamp
    @Column(updatable = false, insertable = true)
    private LocalDateTime createdAt;

    // Enum for status
    public enum AppointmentStatus {
        PENDING, CONFIRMED, CANCELLED, REJECTED, COMPLETED
    }


    public Long getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }
    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    public String getSymptoms() {
        return symptoms;
    }
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    public AppointmentStatus getStatus() {
        return status;
    }
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
	@Override
	public String toString() {
		return "Appointment [appointmentId=" + appointmentId + ", patient=" + patient + ", doctor=" + doctor
				+ ", appointmentDate=" + appointmentDate + ", symptoms=" + symptoms + ", status=" + status
				+ ", createdAt=" + createdAt + "]";
	}
    
}

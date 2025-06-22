package com.hexaware.hospitalmanagement.DTO;

/**
 * AppointmentDTO is a data carrier class for transferring Appointment details with validation.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.time.LocalDateTime;

import com.hexaware.hospitalmanagement.entity.Appointment.AppointmentStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AppointmentDTO {
	    
	    private Long appointmentId;

	    @NotNull(message = "Patient ID is required")
	    private Long patientId;

	    @NotNull(message = "Doctor ID is required")
	    private Long doctorId;

	    @FutureOrPresent(message = "Appointment date cannot be in the past")
	    private LocalDateTime appointmentDate;

	    @Size(max = 255, message = "Symptoms description is too long")
	    private String symptoms;

	    private AppointmentStatus status;

	    

    
    public AppointmentDTO()
    {
    	
    }
	public AppointmentDTO(Long appointmentId, Long patientId, Long doctorId, LocalDateTime appointmentDate,
			String symptoms, AppointmentStatus status) {
		super();
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.appointmentDate = appointmentDate;
		this.symptoms = symptoms;
		this.status = status;
	}
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
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
	@Override
	public String toString() {
		return "AppointmentDTO [appointmentId=" + appointmentId + ", patientId=" + patientId + ", doctorId=" + doctorId
				+ ", appointmentDate=" + appointmentDate + ", symptoms=" + symptoms + ", status=" + status + "]";
	}
    
	
}


package com.hexaware.hospitalmanagement.entity;

/**
 * Entity class representing Patient in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import jakarta.validation.constraints.Pattern;

@Table(name="patients")
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @OneToOne
    @JoinColumn(
        name = "userId",
        unique = true,
        nullable = false, 
        foreignKey = @ForeignKey(name = "fk_patient_user")
    )
    @NotNull(message = "user must be specified")
	 @JsonIgnoreProperties("patient")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String address;

    @NotBlank(message = "Emergency contact is required")
    @Pattern(regexp = "^\\d{10}$", message = "Emergency contact must be a valid phone number")
    @Column(nullable = false, length = 15)
    private String emergencyContact;

    @NotBlank(message = "Blood group is required")
    @Pattern(
        regexp = "^(A|B|AB|O)[+-]$",
        message = "Blood group must be one of: A+, A-, B+, B-, O+, O-, AB+, AB-"
    )
    @Column(
    	    name = "blood_group",
    	    nullable = false,
    	    length = 5,
    	    columnDefinition = "VARCHAR(5) CHECK (blood_group IN ('A+', 'A-', 'B+', 'B-', 'O+', 'O-', 'AB+', 'AB-'))"
    	)
    private String bloodGroup;
    
    @Column(columnDefinition = "TEXT")
    private String medicalHistory;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
	 @JsonIgnoreProperties("patient")
    private Set<Appointment> appointments = new HashSet<>();
	

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return "Patient [patientId=" + patientId + ", user=" + user + ", address=" + address + ", emergencyContact="
				+ emergencyContact + ", bloodGroup=" + bloodGroup + ", medicalHistory=" + medicalHistory + "]";
	}
	
	
    
}



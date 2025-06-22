package com.hexaware.hospitalmanagement.entity;
/**
 * Entity class representing an Doctor in the Hospital Management System.
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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Table(name = "doctors")
@Entity
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long doctorId;

	@OneToOne
	@JoinColumn(name = "userId", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_doctor_user"))
	//"When converting Doctor object to JSON, and going inside user, ignore the doctor field inside that user."
	//When serializing User, ignore the doctor property inside it.
	@JsonIgnoreProperties("doctor")
	private User user;

	@NotBlank(message = "Specialization is required")
	@Column(nullable = false, length = 30)
	private String specialization;

	// Java level validation
	@Min(value = 0, message = "Experience years must be zero or positive")
	// database level
	@Column(name = "experience_years", nullable = false, columnDefinition = "INT CHECK (experience_years >= 0)")
	private int experienceYears;

	@NotBlank(message = "Qualification is required")
	@Column(nullable = false, length = 50)
	private String qualification;

	@NotBlank(message = "Designation is required")
	@Column(nullable = false, length = 50)
	private String designation;

	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	 @JsonIgnoreProperties("doctor")
	private Set<Appointment> appointments = new HashSet<>();

	
	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	@Override
	public String toString() {
		return "Doctor [doctorId=" + doctorId + ", user=" + user + ", specialization=" + specialization
				+ ", experienceYears=" + experienceYears + ", qualification=" + qualification + ", designation="
				+ designation + "]";
	}

}

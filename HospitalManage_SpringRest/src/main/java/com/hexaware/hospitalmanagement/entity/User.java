package com.hexaware.hospitalmanagement.entity;
/**
 * Entity class representing User in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotBlank(message = "Name is required")
	@Size(min = 3, message = "Name must have minimum 3 characters")
	@Column(nullable = false, length = 50)
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Column(nullable = false, unique = true, length = 50)
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must have minimum 6 characters")
	@Column(nullable = false, length = 255)
	private String password;

	@NotNull(message = "Role is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Size(max = 10, message = "Gender must be at most 10 characters")
	@Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
	@Column(length = 10)
	private String gender;

	@NotNull(message = "Date of birth is required")
	@Past(message = "Date of birth must be a past date")
	@Column(nullable = false)
	private LocalDate dateOfBirth;

	@NotBlank(message = "Contact number is required")
	@Pattern(regexp = "\\d{10}", message = "Contact number must have 10 digits")
	@Column(nullable = false, unique = true, length = 10)
	private String contactNumber;

	 @CreationTimestamp
	    @Column(updatable = false, insertable = true)

	    private LocalDateTime createdAt;

	 @OneToOne(mappedBy="user", cascade = CascadeType.ALL) 
	 @JsonIgnoreProperties("user")
	 private Patient patient;
	
	 @OneToOne(mappedBy="user", cascade = CascadeType.ALL) 
	 @JsonIgnoreProperties("user")
	 private Doctor doctor;
	 

	 @OneToOne(mappedBy="user", cascade = CascadeType.ALL) 
	 @JsonIgnoreProperties("user")
	 private Admin admin;
	

	public enum Role {
		PATIENT, DOCTOR, ADMIN;
	}

	// --- Getters and Setters ---

	public Long getUserId() {
		return userId;
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

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password + ", role="
				+ role + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", contactNumber=" + contactNumber
				+ ", createdAt=" + createdAt + "]";
	}

}

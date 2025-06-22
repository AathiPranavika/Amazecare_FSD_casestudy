package com.hexaware.hospitalmanagement.DTO;


/**
 * UserDTO is a data carrier class for transferring User details with validation.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.time.LocalDate;

import com.hexaware.hospitalmanagement.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long userId;
	@NotBlank(message = "Name is required")
	@Size(min = 3, message = "Name must have minimum 3 characters")
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must have minimum 6 characters")
	private String password;

	@NotNull(message = "Role is required")
	private User.Role role;

	@Size(max = 10, message = "Gender must be at most 10 characters")
	@Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
	private String gender;

	@NotNull(message = "Date of birth is required")
	@Past(message = "Date of birth must be a past date")
	private LocalDate dateOfBirth;

	@NotBlank(message = "Contact number is required")
	@Pattern(regexp = "\\d{10}", message = "Contact number must have 10 digits")
	private String contactNumber;

	public Long getUserId() {
		return userId;
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

	public User.Role getRole() {
		return role;
	}

	public void setRole(User.Role role) {
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
}

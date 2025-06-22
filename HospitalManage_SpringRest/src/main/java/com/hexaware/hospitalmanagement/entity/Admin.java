package com.hexaware.hospitalmanagement.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @NotBlank(message = "Admin code is required")
    @Pattern(regexp = "ADM\\d{3}", message = "Admin code must follow pattern ADMxxx (e.g., ADM001)")
    @Column(nullable = false, unique = true, length = 10)
    private String adminCode;

    @NotBlank(message = "Department is required")
    @Column(nullable = false, length = 50)
    private String department;

    @NotBlank(message = "Qualification is required")
    @Column(nullable = false, length = 25)
    private String qualification;

    
    @OneToOne
    @JoinColumn(name = "userId", nullable = false,
                foreignKey = @ForeignKey(name = "fk_admin_user"))
	 @JsonIgnoreProperties("admin")
    private User user;

   
    // --- Getters and Setters ---

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminCode=" + adminCode + ", department=" + department
				+ ", qualification=" + qualification + ", user=" + user + "]";
	}

    
}

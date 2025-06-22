package com.hexaware.hospitalmanagement.entity;

/**
 * Entity class representing an Message in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.hexaware.hospitalmanagement.enums.SenderRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(nullable = false)
    private Long doctorId;

    @Column(nullable = false)
    private Long patientId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,
            columnDefinition = "ENUM('DOCTOR', 'PATIENT')")
    private SenderRole senderRole;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @CreationTimestamp
    @Column(insertable = true, updatable = false)
    private LocalDateTime sentAt;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isRead = false;


    public Message()
    {
    	
    }
	public Message(Long messageId, Long doctorId, Long patientId, SenderRole senderRole, String message,
			LocalDateTime sentAt, boolean isRead) {
		super();
		this.messageId = messageId;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.senderRole = senderRole;
		this.message = message;
		this.sentAt = sentAt;
		this.isRead = isRead;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public SenderRole getSenderRole() {
		return senderRole;
	}

	public void setSenderRole(SenderRole senderRole) {
		this.senderRole = senderRole;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

    
}

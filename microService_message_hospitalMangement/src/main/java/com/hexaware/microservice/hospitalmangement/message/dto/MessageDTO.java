package com.hexaware.microservice.hospitalmangement.message.dto;

import java.time.LocalDateTime;

import com.hexaware.microservice.hospitalmangement.message.entity.Message.SenderRole;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class MessageDTO {

    private Long messageId;

    @NotNull(message = "Doctor ID cannot be null")
    private Long doctorId;

    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;

    @NotNull(message = "Sender role must be doctor or patient")
    private SenderRole senderRole;

    @NotEmpty(message = "Message cannot be empty")
    @Size(max = 500, message = "Message cannot exceed 500 characters")
    private String message;

    private LocalDateTime sentAt;

    private boolean isRead;


    public MessageDTO() {
    }

    public MessageDTO(Long messageId, Long doctorId, Long patientId, SenderRole senderRole, String message, LocalDateTime sentAt, boolean isRead) {
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

    public void setMessageId(Long id) {
        this.messageId = id;
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

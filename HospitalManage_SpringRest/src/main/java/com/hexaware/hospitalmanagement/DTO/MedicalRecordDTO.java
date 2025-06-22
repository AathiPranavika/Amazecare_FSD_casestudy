package com.hexaware.hospitalmanagement.DTO;


/**
 * MedicalRecordDTO is a data carrier class for transferring MedicalRecord details with validation.
 * @author Aathi Pranavika
 * @version 1.0
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MedicalRecordDTO {

    private Long recordId;

    @NotNull(message = "Appointment ID must not be null")
    private Long appointmentId;

    private String symptoms;

    private String physicalExam;

    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    private String treatmentPlan;


    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
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

   

    @Override
    public String toString() {
        return "MedicalRecordDTO [recordId=" + recordId + ", appointmentId=" + appointmentId
                + ", symptoms=" + symptoms + ", physicalExam=" + physicalExam
                + ", diagnosis=" + diagnosis + ", treatmentPlan=" + treatmentPlan + "]";
    }
}

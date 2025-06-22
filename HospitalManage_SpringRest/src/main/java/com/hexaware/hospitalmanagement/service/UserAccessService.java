package com.hexaware.hospitalmanagement.service;

/**
 * Service for verifying whether a user has access to a specific resource such as a patient, doctor, appointment, prescription, or medical record.
 * @author Aathi Pranavika
 * @version 1.0
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hospitalmanagement.entity.Appointment;
import com.hexaware.hospitalmanagement.entity.MedicalRecord;
import com.hexaware.hospitalmanagement.entity.Prescription;
import com.hexaware.hospitalmanagement.repository.AppointmentRepository;
import com.hexaware.hospitalmanagement.repository.DoctorRepository;
import com.hexaware.hospitalmanagement.repository.MedicalRecordRepository;
import com.hexaware.hospitalmanagement.repository.PatientRepository;
import com.hexaware.hospitalmanagement.repository.PrescriptionRepository;
@Service("userAccessService")
public class UserAccessService {

    @Autowired
    private PatientRepository patientRepository;  

    @Autowired
    private DoctorRepository doctorRepository;  
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    // Check if userId owns this patientId
    public boolean userOwnsPatient(Long userId, Long patientId) {
        return patientRepository.existsByPatientIdAndUserUserId(patientId, userId);
    }

    public boolean userOwnsDoctor(Long userId, Long doctorId) {
        return doctorRepository.existsByDoctorIdAndUserUserId(doctorId, userId);
    }
    
    public boolean userOwnsAppointment(Long userId, Long appointmentId) {
        Appointment appt = appointmentRepository.findById(appointmentId).orElse(null);
        if (appt == null) return false;
        Long patientUserId = appt.getPatient().getUser().getUserId();
        return patientUserId.equals(userId);
    }
    
    public boolean userOwnsPrescription(Long userId, Long prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId).orElse(null);
        if (prescription == null) return false;

        Long doctorUserId = prescription.getMedicalRecord().getAppointment()
                                         .getDoctor()
                                         .getUser()
                                         .getUserId();

        return doctorUserId.equals(userId);
    }
    
    public boolean userOwnsMedicalRecord(Long userId, Long recordId) {
        MedicalRecord record = medicalRecordRepository.findById(recordId).orElse(null);
        if (record == null) return false;

        Long doctorUserId = record.getAppointment().getDoctor().getUser().getUserId();

        return doctorUserId.equals(userId);
    }
    
    public boolean doctorUserOwnsAppointment(Long userId, Long appointmentId) {
        Appointment appt = appointmentRepository.findById(appointmentId).orElse(null);
        if (appt == null) return false;
        Long doctorUserId = appt.getDoctor().getUser().getUserId();
        return doctorUserId.equals(userId);
    }



}

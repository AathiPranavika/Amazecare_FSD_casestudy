package com.hexaware.hospitalmanagement.service;
import com.hexaware.hospitalmanagement.DTO.MedicalRecordDTO;
import com.hexaware.hospitalmanagement.entity.MedicalRecord;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.exception.MedicalRecordNotFoundException;

import java.util.List;

public interface IMedicalRecordService {

    MedicalRecord createMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws AppointmentNotFoundException;

    MedicalRecord getMedicalRecordByAppointmentId(Long appointmentId) throws MedicalRecordNotFoundException;

    MedicalRecord getMedicalRecordById(Long recordId) throws MedicalRecordNotFoundException;

    List<MedicalRecordDTO> getMedicalRecordsByPatientId(Long patientId);

    List<MedicalRecord> getMedicalRecordsByDoctorId(Long doctorId);

    MedicalRecord updateMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws MedicalRecordNotFoundException, AppointmentNotFoundException;

    boolean deleteMedicalRecord(Long recordId) throws MedicalRecordNotFoundException;

    List<MedicalRecord> getAllMedicalRecords();
}

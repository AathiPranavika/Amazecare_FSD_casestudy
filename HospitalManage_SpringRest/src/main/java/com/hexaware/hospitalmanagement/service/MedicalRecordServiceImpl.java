package com.hexaware.hospitalmanagement.service;
import java.util.ArrayList;
/**
 * Service implementation for managing medical record operations in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hospitalmanagement.DTO.MedicalRecordDTO;
import com.hexaware.hospitalmanagement.entity.Appointment;
import com.hexaware.hospitalmanagement.entity.MedicalRecord;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.exception.MedicalRecordNotFoundException;
import com.hexaware.hospitalmanagement.repository.AppointmentRepository;
import com.hexaware.hospitalmanagement.repository.MedicalRecordRepository;

@Service
public class MedicalRecordServiceImpl implements IMedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private MedicalRecordDTO convertToDTO(MedicalRecord record) {
        MedicalRecordDTO dto = new MedicalRecordDTO();
        dto.setRecordId(record.getRecordId());

        if (record.getAppointment() != null) {
            dto.setAppointmentId(record.getAppointment().getAppointmentId());
        }

        dto.setSymptoms(record.getSymptoms());
        dto.setPhysicalExam(record.getPhysicalExam());
        dto.setDiagnosis(record.getDiagnosis());
        dto.setTreatmentPlan(record.getTreatmentPlan());

        return dto;
    }

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecordDTO dto) throws AppointmentNotFoundException {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(dto.getAppointmentId());

        if (optionalAppointment.isEmpty()) {
            throw new AppointmentNotFoundException("Appointment not found with id: " + dto.getAppointmentId());
        }

        Appointment appointment = optionalAppointment.get();

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAppointment(appointment);
        medicalRecord.setSymptoms(dto.getSymptoms());
        medicalRecord.setPhysicalExam(dto.getPhysicalExam());
        medicalRecord.setDiagnosis(dto.getDiagnosis());
        medicalRecord.setTreatmentPlan(dto.getTreatmentPlan());
        
        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public MedicalRecord getMedicalRecordByAppointmentId(Long appointmentId) throws MedicalRecordNotFoundException {
        Optional<MedicalRecord> optionalRecord = medicalRecordRepository.findByAppointmentAppointmentId(appointmentId);

        if (optionalRecord.isEmpty()) {
            throw new MedicalRecordNotFoundException("Medical record not found for appointment id: " + appointmentId);
        }

        return optionalRecord.get();
    }

    @Override
    public MedicalRecord getMedicalRecordById(Long recordId) throws MedicalRecordNotFoundException {
        Optional<MedicalRecord> optionalRecord = medicalRecordRepository.findById(recordId);

        if (optionalRecord.isEmpty()) {
            throw new MedicalRecordNotFoundException("Medical record not found with id: " + recordId);
        }

        return optionalRecord.get();
    }

    @Override
    public List<MedicalRecordDTO> getMedicalRecordsByPatientId(Long patientId) {
        List<MedicalRecord> records = medicalRecordRepository.findByAppointmentPatientPatientId(patientId);

        List<MedicalRecordDTO> dtoList = new ArrayList<>();
        for (MedicalRecord record : records) {
            MedicalRecordDTO dto = convertToDTO(record);
            dtoList.add(dto);
        }

        return dtoList;
    }


    @Override
    public List<MedicalRecord> getMedicalRecordsByDoctorId(Long doctorId) {
        return medicalRecordRepository.findByAppointmentDoctorDoctorId(doctorId);
    }

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecordDTO dto) throws MedicalRecordNotFoundException, AppointmentNotFoundException {
    	MedicalRecord existing = medicalRecordRepository.findById(dto.getRecordId())
    		    .orElseThrow(() -> new MedicalRecordNotFoundException("Medical record not found with id: " + dto.getRecordId()));

    		existing.setSymptoms(dto.getSymptoms());
    		existing.setPhysicalExam(dto.getPhysicalExam());
    		existing.setDiagnosis(dto.getDiagnosis());
    		existing.setTreatmentPlan(dto.getTreatmentPlan());

    		if (dto.getAppointmentId() != null && !dto.getAppointmentId().equals(existing.getAppointment().getAppointmentId())) {
    		    Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
    		        .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + dto.getAppointmentId()));
    		    existing.setAppointment(appointment);
    		}

    		return medicalRecordRepository.save(existing); 

    }

    @Override
    public boolean deleteMedicalRecord(Long recordId) throws MedicalRecordNotFoundException {
        if (!medicalRecordRepository.existsById(recordId)) {
            throw new MedicalRecordNotFoundException("Medical record not found with id: " + recordId);
        }
        medicalRecordRepository.deleteById(recordId);
        return true;
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }
}

package com.hexaware.hospitalmanagement.restcontroller;
/**
* REST controller for medicalRecords-related operations in the Hospital Management System.
* * 
* @author Aathi Pranavika
* @version 1.0
* */
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.hospitalmanagement.DTO.MedicalRecordDTO;
import com.hexaware.hospitalmanagement.entity.MedicalRecord;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.exception.MedicalRecordNotFoundException;
import com.hexaware.hospitalmanagement.service.IMedicalRecordService;

import jakarta.validation.Valid;
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/medicalRecords")
public class MedicalRecordController {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @Autowired
    private IMedicalRecordService medicalRecordService;

    @PostMapping("/create")
    public MedicalRecord createMedicalRecord(@Valid @RequestBody MedicalRecordDTO dto) throws AppointmentNotFoundException {
        logger.info("Creating medical record for appointmentId={}", dto.getAppointmentId());
        return medicalRecordService.createMedicalRecord(dto);
    }

    @GetMapping("/getbyid/{recordId}")
    public MedicalRecord getMedicalRecordById(@PathVariable Long recordId) throws MedicalRecordNotFoundException {
        logger.info("Fetching medical record by recordId={}", recordId);
        return medicalRecordService.getMedicalRecordById(recordId);
    }

    @GetMapping("/byAppointment/{appointmentId}")
    public MedicalRecord getMedicalRecordByAppointmentId(@PathVariable Long appointmentId) throws MedicalRecordNotFoundException {
        logger.info("Fetching medical record by appointmentId={}", appointmentId);
        return medicalRecordService.getMedicalRecordByAppointmentId(appointmentId);
    }

    @GetMapping("/byPatient/{patientId}")
    public List<MedicalRecordDTO> getMedicalRecordsByPatientId(@PathVariable Long patientId) {
        logger.info("Fetching all medical records for patientId={}", patientId);
        return medicalRecordService.getMedicalRecordsByPatientId(patientId);
    }

    @GetMapping("/byDoctor/{doctorId}")
    public List<MedicalRecord> getMedicalRecordsByDoctorId(@PathVariable Long doctorId) {
        logger.info("Fetching all medical records for doctorId={}", doctorId);
        return medicalRecordService.getMedicalRecordsByDoctorId(doctorId);
    }

    @PutMapping("/update/{medicalRecordId}")
    public MedicalRecord updateMedicalRecord(@PathVariable Long medicalRecordId,@Valid @RequestBody MedicalRecordDTO dto) throws MedicalRecordNotFoundException, AppointmentNotFoundException {
        logger.info("Updating medical record for recordId={}", dto.getRecordId());
        return medicalRecordService.updateMedicalRecord(dto);
    }

    @DeleteMapping("/delete/{recordId}")
    public String deleteMedicalRecord(@PathVariable Long recordId) throws MedicalRecordNotFoundException {
        logger.info("Deleting medical record with recordId={}", recordId);
        boolean deleted = medicalRecordService.deleteMedicalRecord(recordId);
        return deleted ? "Medical record deleted successfully" : "Failed to delete medical record";
    }

    @GetMapping("/getall")
    public List<MedicalRecord> getAllMedicalRecords() {
        logger.info("Fetching all medical records");
        return medicalRecordService.getAllMedicalRecords();
    }
}

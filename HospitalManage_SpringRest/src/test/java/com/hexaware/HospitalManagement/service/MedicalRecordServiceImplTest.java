package com.hexaware.HospitalManagement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.hexaware.hospitalmanagement.DTO.MedicalRecordDTO;
import com.hexaware.hospitalmanagement.entity.MedicalRecord;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.exception.MedicalRecordNotFoundException;
import com.hexaware.hospitalmanagement.service.IMedicalRecordService;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MedicalRecordServiceImplTest {

    @Autowired
    private IMedicalRecordService medicalRecordService;


    @Test
    @Order(1)
    void testCreateMedicalRecord() throws AppointmentNotFoundException {
        MedicalRecordDTO dto = new MedicalRecordDTO();
        dto.setSymptoms("Cough, cold");
        dto.setPhysicalExam("Normal");
        dto.setDiagnosis("Viral Infection");
        dto.setTreatmentPlan("Rest and fluids");
        dto.setAppointmentId(5L);
        MedicalRecord saved = medicalRecordService.createMedicalRecord(dto);
        assertNotNull(saved);
    }

    @Test
    @Order(2)
    void testGetMedicalRecordById() throws MedicalRecordNotFoundException {
        MedicalRecord record = medicalRecordService.getMedicalRecordById(3L);
        assertNotNull(record);
    }

    @Test
    @Order(3)
    void testUpdateMedicalRecord() throws MedicalRecordNotFoundException, AppointmentNotFoundException {
        MedicalRecordDTO dto = new MedicalRecordDTO();
        dto.setRecordId(3L);
        dto.setAppointmentId(2L);
        dto.setDiagnosis("Stress");
       
        MedicalRecord updated = medicalRecordService.updateMedicalRecord(dto);
        assertEquals("Stress", updated.getDiagnosis());
    }

    @Test
    @Order(4)
    void testGetMedicalRecordsByAppointmentId() throws MedicalRecordNotFoundException {
        MedicalRecord record = medicalRecordService.getMedicalRecordByAppointmentId(2L);
        assertNotNull(record);
    }

    @Test
    @Order(5)
    void testGetAllMedicalRecords() {
        List<MedicalRecord> records = medicalRecordService.getAllMedicalRecords();
        assertNotNull(records);
        assertFalse(records.isEmpty());
    }

    @Test
    @Order(6)
    void testDeleteMedicalRecord() throws MedicalRecordNotFoundException {
        boolean deleted = medicalRecordService.deleteMedicalRecord(3L);
        assertTrue(deleted);
    }
}

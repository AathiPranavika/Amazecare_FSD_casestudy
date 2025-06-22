package com.hexaware.HospitalManagement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.hexaware.hospitalmanagement.DTO.PatientDTO;
import com.hexaware.hospitalmanagement.entity.Patient;
import com.hexaware.hospitalmanagement.exception.DuplicatePatientException;
import com.hexaware.hospitalmanagement.exception.InvalidRoleException;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;
import com.hexaware.hospitalmanagement.service.IPatientService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(OrderAnnotation.class)
class PatientServiceImplTest {

    @Autowired
    private IPatientService patientService;


    @Test
    @Order(1)
    void testRegisterPatient() throws DuplicatePatientException, UserNotFoundException, InvalidRoleException {
        PatientDTO dto = new PatientDTO();
        dto.setUserId(36L);
        dto.setAddress("123 Health Street");
        dto.setEmergencyContact("9876543210");
        dto.setBloodGroup("A+");
        dto.setMedicalHistory("No known allergies");

        Patient savedPatient = patientService.registerPatient(dto);
        assertNotNull(savedPatient);
        assertEquals("123 Health Street", savedPatient.getAddress());
    }

    @Test
    @Order(2)
    void testUpdatePatient() {
        PatientDTO updateDto = new PatientDTO();
        updateDto.setBloodGroup("B+");
        updateDto.setMedicalHistory("Updated history");

        Patient updated = patientService.updatePatient(2L, updateDto);
        assertNotNull(updated);
        assertEquals("B+", updated.getBloodGroup());
    }

    @Test
    @Order(3)
    void testGetPatientById() {
        Patient patient = patientService.getPatientById(1L);
        assertNotNull(patient);
        assertEquals(1, patient.getUser().getUserId());
    }

    @Test
    @Order(4)
    void testGetAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        assertNotNull(patients);
        assertFalse(patients.isEmpty());
    }

    @Test
    @Order(5)
    void testSearchPatientsByBloodGroup() {
        List<Patient> patients = patientService.searchPatientsByBloodGroup("B+");
        assertNotNull(patients);
    }

    @Test
    @Order(6)
    void testDeletePatient() {
        boolean deleted = patientService.deletePatient(1L);
        assertTrue(deleted);
    }

    @Test
    @Order(7)
    void testSearchPatientsByName() {
        List<Patient> patients = patientService.searchPatientsByName("John");
        assertNotNull(patients); 
    }
}

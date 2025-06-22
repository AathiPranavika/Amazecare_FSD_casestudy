package com.hexaware.HospitalManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.hexaware.hospitalmanagement.DTO.DoctorDTO;
import com.hexaware.hospitalmanagement.entity.Doctor;
import com.hexaware.hospitalmanagement.exception.DuplicateDoctorException;
import com.hexaware.hospitalmanagement.exception.InvalidRoleException;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;
import com.hexaware.hospitalmanagement.service.IDoctorService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback
class DoctorServiceImplTest {

	@Autowired
	private IDoctorService doctorService;

	@Test
	@Order(1)
	void testRegisterDoctor() throws UserNotFoundException, InvalidRoleException, DuplicateDoctorException {
		DoctorDTO dto = new DoctorDTO();
		dto.setSpecialization("Cardiology");
		dto.setDesignation("Senior Consultant");
		dto.setExperienceYears(15);
		dto.setQualification("MD");
		dto.setUserId(34L);
		Doctor savedDoctor = doctorService.registerDoctor(dto);
		assertNotNull(savedDoctor);
		assertNotNull(savedDoctor.getDoctorId());

	}

	@Test

	@Order(2)
	void testGetDoctorById() {

		Doctor doctor = doctorService.getDoctorById(1L);
		assertNotNull(doctor);
		assertEquals("Cardiology", doctor.getSpecialization());
	}

	@Test

	@Order(3)
	void testUpdateDoctor() throws UserNotFoundException {
		DoctorDTO updateDto = new DoctorDTO();
		updateDto.setSpecialization("Neurology");
		updateDto.setQualification("MD");
		updateDto.setUserId(34L);

		Doctor updatedDoctor = doctorService.updateDoctor(2L, updateDto);
		assertNotNull(updatedDoctor);
		assertEquals("Neurology", updatedDoctor.getSpecialization());
	}

	@Test

	@Order(4)
	void testGetDoctorsBySpecialization() {
		List<Doctor> doctors = doctorService.getDoctorsBySpecialization("Neurology");
		assertTrue(doctors.size() > 0);
	}

	@Test

	@Order(5)
	void testGetDoctorsByDesignation() {
		List<Doctor> doctors = doctorService.getDoctorsByDesignation("Cardiologist");
		assertTrue(doctors.size() > 0);
	}

	@Test

	@Order(6)
	void testGetDoctorsByGender() {
		List<Doctor> doctors = doctorService.getDoctorsByGender("Male");
		assertTrue(doctors.size() > 0);
	}

	@Test

	@Order(7)
	void testDeleteDoctor() {
		boolean deleted = doctorService.deleteDoctor(1L);
		assertTrue(deleted);

	}
}

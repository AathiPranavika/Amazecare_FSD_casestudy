package com.hexaware.hospitalmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.hospitalmanagement.entity.Prescription;
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

	List<Prescription> findByMedicineName(String medicineName);
	
	List<Prescription> findByMedicalRecordAppointmentPatientPatientId(Long patientId);
	List<Prescription> findByMedicalRecordAppointmentAppointmentId(Long appointmentId);

}

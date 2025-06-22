package com.hexaware.hospitalmanagement.service;
/**
 * Service interface for managing doctor-related operations in the Hospital Management System.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.util.List;

import com.hexaware.hospitalmanagement.DTO.DoctorDTO;
import com.hexaware.hospitalmanagement.DTO.MedicalRecordDTO;
import com.hexaware.hospitalmanagement.DTO.MessageDTO;
import com.hexaware.hospitalmanagement.DTO.PrescriptionDTO;
import com.hexaware.hospitalmanagement.entity.Appointment;
import com.hexaware.hospitalmanagement.entity.Doctor;
import com.hexaware.hospitalmanagement.entity.MedicalRecord;
import com.hexaware.hospitalmanagement.entity.Patient;
import com.hexaware.hospitalmanagement.entity.Prescription;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.exception.DuplicateDoctorException;
import com.hexaware.hospitalmanagement.exception.InvalidRoleException;
import com.hexaware.hospitalmanagement.exception.MedicalRecordNotFoundException;
import com.hexaware.hospitalmanagement.exception.PrescriptionNotFoundException;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;

public interface IDoctorService {

	Doctor getDoctorById(Long doctorId);

	Doctor updateDoctor(Long doctorId, DoctorDTO doctorDTO) throws UserNotFoundException;

	boolean deleteDoctor(Long doctorId);

	boolean checkDoctorExistsById(Long doctorId);

	List<Doctor> getDoctorsBySpecialization(String specialization);

	List<Doctor> getDoctorsByDesignation(String designation);

	List<Doctor> getDoctorsByGender(String gender);

	Doctor registerDoctor(DoctorDTO doctorDTO)
			throws UserNotFoundException, InvalidRoleException, DuplicateDoctorException;

	List<Doctor> getAllDoctors();
	
	 boolean existsByUserUserId(Long userId);
	 
	Doctor getDoctorByUserId(Long userId);
	List<Doctor> searchDoctorsByName(String name);

	List<Appointment> getAppointments(Long doctorId);

	List<Appointment> getUpcomingAppointments(Long doctorId);

    MedicalRecord addMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws AppointmentNotFoundException;

    MedicalRecord updateMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws MedicalRecordNotFoundException, AppointmentNotFoundException;

	List<MedicalRecordDTO> getPatientMedicalHistory(Long patientId);

	Prescription addPrescription(PrescriptionDTO prescriptionDTO) throws MedicalRecordNotFoundException;

	Prescription updatePrescription(PrescriptionDTO updatedPrescription) throws PrescriptionNotFoundException;


	MessageDTO sendMessage(MessageDTO messageDTO);

	List<MessageDTO> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId);

	List<MessageDTO> getUnreadMessagesForDoctor(int doctorId);

	boolean markMessageAsRead(int messageId);

	List<MessageDTO> getMessagesSentByDoctor(int doctorId);
	
	
	List<Patient> searchPatientsByName(String name);

    List<Patient> searchPatientsByBloodGroup(String bloodGroup);

	

   


}

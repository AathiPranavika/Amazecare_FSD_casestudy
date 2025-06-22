package com.hexaware.hospitalmanagement.service;
/**
 * Service interface for managing patient-related operations in the Hospital Management System.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.util.List;

import com.hexaware.hospitalmanagement.DTO.AppointmentDTO;
import com.hexaware.hospitalmanagement.DTO.MedicalRecordDTO;
import com.hexaware.hospitalmanagement.DTO.MessageDTO;
import com.hexaware.hospitalmanagement.DTO.PatientDTO;
import com.hexaware.hospitalmanagement.entity.Appointment;
import com.hexaware.hospitalmanagement.entity.Doctor;
import com.hexaware.hospitalmanagement.entity.Patient;
import com.hexaware.hospitalmanagement.entity.Prescription;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.exception.DuplicatePatientException;
import com.hexaware.hospitalmanagement.exception.InvalidRoleException;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;

public interface IPatientService {

    Patient registerPatient(PatientDTO patientInput) throws InvalidRoleException, UserNotFoundException, DuplicatePatientException;

    Patient getPatientById(Long patientId);

    Patient updatePatient(Long patientId, PatientDTO patientInput);

    boolean deletePatient(Long patientId);

    List<Patient> getAllPatients();

    List<Patient> searchPatientsByName(String name);

    List<Patient> searchPatientsByBloodGroup(String bloodGroup);

    Appointment bookAppointment(AppointmentDTO appointmentDto) throws AppointmentNotFoundException;

    boolean cancelAppointment(Long appointmentId) throws AppointmentNotFoundException;

    List<Appointment> getUpcomingAppointments(Long patientId);

    List<MedicalRecordDTO> getMedicalRecordsByPatientId(Long patientId);

    List<Prescription> getPrescriptionsByPatientId(Long patientId);
        
    MessageDTO sendMessage(MessageDTO messageDTO);

    List<MessageDTO> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId);

    List<MessageDTO> getUnreadMessagesForPatient(int patientId);

    boolean markMessageAsRead(int messageId);

    List<MessageDTO> getMessagesSentByPatient(int patientId);

	boolean checkPatientExistsById(Long patientId);
	
	boolean existsByUserUserId(Long id);

    List<Doctor> getDoctorsBySpecialization(String specialization);

    List<Doctor> getDoctorsByDesignation(String designation);

    List<Doctor> getDoctorsByGender(String gender);

    List<Doctor> getAllDoctors();

    List<Doctor> searchDoctorsByName(String name);

	Patient getPatientByUserId(Long userId);

}

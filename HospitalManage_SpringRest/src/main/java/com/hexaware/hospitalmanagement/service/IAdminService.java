package com.hexaware.hospitalmanagement.service;
/**
 * Service interface for managing admin-related operations in the Hospital Management System.
 * * 
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.time.LocalDateTime;
import java.util.List;

import com.hexaware.hospitalmanagement.DTO.AdminDTO;
import com.hexaware.hospitalmanagement.DTO.AppointmentDTO;
import com.hexaware.hospitalmanagement.DTO.DoctorDTO;
import com.hexaware.hospitalmanagement.DTO.PatientDTO;
import com.hexaware.hospitalmanagement.DTO.UserDTO;
import com.hexaware.hospitalmanagement.entity.Admin;
import com.hexaware.hospitalmanagement.entity.Appointment;
import com.hexaware.hospitalmanagement.entity.Doctor;
import com.hexaware.hospitalmanagement.entity.Patient;
import com.hexaware.hospitalmanagement.entity.User;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.exception.DuplicateDoctorException;
import com.hexaware.hospitalmanagement.exception.DuplicatePatientException;
import com.hexaware.hospitalmanagement.exception.InvalidRoleException;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;

public interface IAdminService {

    Doctor addDoctor(DoctorDTO doctorDTO) throws UserNotFoundException, InvalidRoleException, DuplicateDoctorException;
    Doctor updateDoctor(Long doctorId, DoctorDTO doctorDTO) throws UserNotFoundException;
    void deleteDoctor(Long doctorId);
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(Long doctorId);
    List<Doctor> getDoctorsBySpecialization(String specialization);
    List<Doctor> getDoctorsByGender(String gender);

    Patient addPatient(PatientDTO patientDTO) throws InvalidRoleException, UserNotFoundException, DuplicatePatientException;
    Patient updatePatient(Long patientId, PatientDTO patientDTO);
    void deletePatient(Long patientId);
    List<Patient> getAllPatients();
    Patient getPatientById(Long patientId);
    List<Patient> searchPatientsByBloodGroup(String bloodGroup);

    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(Long appointmentId) throws AppointmentNotFoundException;
    Appointment updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO) throws AppointmentNotFoundException;
    Appointment cancelAppointment(Long appointmentId) throws AppointmentNotFoundException;
    Appointment rejectAppointmentById(Long appointmentId) throws AppointmentNotFoundException;
    
    Appointment completeAppointmentById(Long appointmentId) throws AppointmentNotFoundException;
    
    Appointment confirmAppointment(Long id,AppointmentDTO appointmentDTO, LocalDateTime dateTime) throws AppointmentNotFoundException;
	boolean deleteAppointmentById(Long appointmentId) throws AppointmentNotFoundException;


    String addUser(UserDTO userDTO);
    User updateUser(Long userId, UserDTO userDTO) throws UserNotFoundException;
    void deleteUser(Long userId) throws UserNotFoundException;
    User getUserById(Long userId) throws UserNotFoundException;
    List<User> getAllUsers();

    Admin registerAdmin(AdminDTO adminDTO) throws UserNotFoundException;
    Admin updateAdmin(Long adminId, AdminDTO adminDTO) throws UserNotFoundException;
    boolean deleteAdmin(Long adminId);
    List<Admin> getAllAdmins();
    Admin getAdminById(Long adminId);
    boolean existsByUserUserId(Long userId);
    Admin getAdminByUserId(Long userId);
    
}

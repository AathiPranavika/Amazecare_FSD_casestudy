package com.hexaware.hospitalmanagement.service;
/**
 * Service implementation for managing Admin operations in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.hexaware.hospitalmanagement.exception.AdminNotFoundException;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.exception.DuplicateDoctorException;
import com.hexaware.hospitalmanagement.exception.DuplicatePatientException;
import com.hexaware.hospitalmanagement.exception.InvalidRoleException;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;
import com.hexaware.hospitalmanagement.repository.AdminRepository;

@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	IDoctorService doctorService;
	@Autowired
	IPatientService patientService;
	@Autowired
    IAppointmentService appointmentService;
	
	@Autowired
	IUserService userService;
		
	@Autowired
    private AdminRepository adminRepo;

	@Override
	public Admin registerAdmin(AdminDTO adminDTO) throws UserNotFoundException {
	    Optional<Admin> existing = adminRepo.findById(adminDTO.getUserId());
	    if (existing.isPresent()) {
	        throw new IllegalArgumentException("Admin with this userId already exists");
	    }

	    User userOpt = userService.getUserById(adminDTO.getUserId());
	    User user = userOpt;

	    if (user.getRole() != User.Role.ADMIN) {
	        throw new IllegalArgumentException("User role is not ADMIN");
	    }

	    Admin admin = new Admin();
	    admin.setAdminCode(adminDTO.getAdminCode());
	    admin.setDepartment(adminDTO.getDepartment());
	    admin.setQualification(adminDTO.getQualification());
	    admin.setUser(user);

	    return adminRepo.save(admin);
	}


	    @Override
	    public Admin updateAdmin(Long adminId, AdminDTO adminDTO) throws UserNotFoundException {
	    	Admin existing = adminRepo.findById(adminId)
	    		    .orElseThrow(() -> new AdminNotFoundException("Admin not found with ID: " + adminId));

	    		existing.setAdminCode(adminDTO.getAdminCode());
	    		existing.setDepartment(adminDTO.getDepartment());
	    		existing.setQualification(adminDTO.getQualification());

	    		return adminRepo.save(existing); 
	    }

	    @Override
	    public boolean deleteAdmin(Long adminId) {
	        if (!adminRepo.existsById(adminId)) {
	            throw new AdminNotFoundException("Admin not found with ID: " + adminId);
	        }
	        adminRepo.deleteById(adminId);
			return true;
	    }

	    @Override
	    public List<Admin> getAllAdmins() {
	        return adminRepo.findAll();
	    }

	    @Override
	    public Admin getAdminById(Long adminId) {
	        Optional<Admin> optionalAdmin = adminRepo.findById(adminId);

	        if (optionalAdmin.isPresent()) {
	            return optionalAdmin.get();
	        } else {
	            throw new AdminNotFoundException("Admin not found with ID: " + adminId);
	        }
	    }
	    @Override
	    public boolean existsByUserUserId(Long userId) {
	        return adminRepo.existsByUserUserId(userId);
	    }

	    @Override
	    public Admin getAdminByUserId(Long userId) {
	        return adminRepo.findByUserUserId(userId)
	                .orElseThrow(() -> new AdminNotFoundException("Admin not found with userId: " + userId));
	    }

	@Override
	public Doctor addDoctor(DoctorDTO doctorDTO) throws UserNotFoundException, InvalidRoleException, DuplicateDoctorException {
		return doctorService.registerDoctor(doctorDTO);
	}

	@Override
	public Doctor updateDoctor(Long doctorId, DoctorDTO doctorDTO) throws UserNotFoundException {
		return doctorService.updateDoctor(doctorId, doctorDTO);
	}

	@Override
	public void deleteDoctor(Long doctorId) {
		doctorService.deleteDoctor(doctorId);
	}

	@Override
	public List<Doctor> getAllDoctors() {
		return doctorService.getAllDoctors();
	}

	@Override
	public Doctor getDoctorById(Long doctorId) {
		return doctorService.getDoctorById(doctorId);
	}

	@Override
	public List<Doctor> getDoctorsBySpecialization(String specialization) {
		return doctorService.getDoctorsBySpecialization(specialization);
	}

	@Override
	public List<Doctor> getDoctorsByGender(String gender) {
		return doctorService.getDoctorsByGender(gender);
	}

	@Override
	public Patient addPatient(PatientDTO patientDTO) throws InvalidRoleException, UserNotFoundException, DuplicatePatientException {
		return patientService.registerPatient(patientDTO);
	}

	@Override
	public Patient updatePatient(Long patientId, PatientDTO patientDTO) {
		return patientService.updatePatient(patientId, patientDTO);
	}

	@Override
	public void deletePatient(Long patientId) {
		patientService.deletePatient(patientId);	
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientService.getAllPatients();
	}

	@Override
	public Patient getPatientById(Long patientId) {
		return patientService.getPatientById(patientId);
	}

	@Override
	public List<Patient> searchPatientsByBloodGroup(String bloodGroup) {
		return patientService.searchPatientsByBloodGroup(bloodGroup);
	}

	@Override
	public List<Appointment> getAllAppointments() {
		return appointmentService.getAllAppointments();
	}

	@Override
	public Appointment getAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
		return appointmentService.getAppointmentById(appointmentId);
	}

	@Override
	public Appointment updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO) throws AppointmentNotFoundException {
		return appointmentService.updateAppointment(appointmentId, appointmentDTO);
	}

	@Override
	public Appointment cancelAppointment(Long appointmentId) throws AppointmentNotFoundException {
		return appointmentService.cancelAppointmentById(appointmentId);	
	}
	
	@Override
	public Appointment rejectAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
		return appointmentService.rejectAppointmentById(appointmentId);
		 
	}

	@Override
	public Appointment completeAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
		return appointmentService.completeAppointmentById(appointmentId);
	}

	@Override
	public Appointment confirmAppointment(Long id,AppointmentDTO appointmentDTO, LocalDateTime dateTime)
			throws AppointmentNotFoundException {
		return appointmentService.confirmAppointment(id,appointmentDTO, dateTime);
	}
	
	@Override
	public boolean deleteAppointmentById(Long appointmentId) throws AppointmentNotFoundException {
	    
	    appointmentService.deleteAppointmentById(appointmentId);
	    return true;
	}

	@Override
	public String addUser(UserDTO userDTO) {
		return userService.registerUser(userDTO);
	}

	@Override
	public User updateUser(Long userId, UserDTO userDTO) throws UserNotFoundException {
		return userService.updateUser(userId, userDTO);
	}

	@Override
	public void deleteUser(Long userId) throws UserNotFoundException {
		userService.deleteUser(userId);
	}

	@Override
	public User getUserById(Long userId) throws UserNotFoundException {
		return userService.getUserById(userId);
	}

	@Override
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	

	

   
}

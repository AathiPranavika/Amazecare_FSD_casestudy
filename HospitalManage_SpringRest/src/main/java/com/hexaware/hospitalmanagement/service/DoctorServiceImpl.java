package com.hexaware.hospitalmanagement.service;
/**
 * Service implementation for managing doctor operations in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.hexaware.hospitalmanagement.DTO.DoctorDTO;
import com.hexaware.hospitalmanagement.DTO.MedicalRecordDTO;
import com.hexaware.hospitalmanagement.DTO.MessageDTO;
import com.hexaware.hospitalmanagement.DTO.PrescriptionDTO;
import com.hexaware.hospitalmanagement.entity.Appointment;
import com.hexaware.hospitalmanagement.entity.Doctor;
import com.hexaware.hospitalmanagement.entity.MedicalRecord;
import com.hexaware.hospitalmanagement.entity.Message;
import com.hexaware.hospitalmanagement.entity.Patient;
import com.hexaware.hospitalmanagement.entity.Prescription;
import com.hexaware.hospitalmanagement.entity.User;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.exception.DoctorNotFoundException;
import com.hexaware.hospitalmanagement.exception.DuplicateDoctorException;
import com.hexaware.hospitalmanagement.exception.MedicalRecordNotFoundException;
import com.hexaware.hospitalmanagement.exception.PrescriptionNotFoundException;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;
import com.hexaware.hospitalmanagement.repository.AppointmentRepository;
import com.hexaware.hospitalmanagement.repository.DoctorRepository;
import com.hexaware.hospitalmanagement.repository.PatientRepository;
import com.hexaware.hospitalmanagement.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoctorServiceImpl implements IDoctorService {

	@Autowired
	DoctorRepository doctorRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	AppointmentRepository AppointmentRepo;

	@Autowired
	IMedicalRecordService MedicalRecordService;

	@Autowired
	IPrescriptionService PrescriptionService;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PatientRepository patientRepository;

	private static final String MESSAGESERVICEURL = "http://localhost:8282/api/messages";

	private MessageDTO convertToDTO(Message message) {
	    if (message == null) return null;

	    MessageDTO dto = new MessageDTO();
	    dto.setMessageId(message.getMessageId());
	    dto.setDoctorId(message.getDoctorId());
	    dto.setPatientId(message.getPatientId());
	    dto.setMessage(message.getMessage());
	    dto.setSentAt(message.getSentAt());
	    dto.setRead(message.isRead());
	    dto.setSenderRole(message.getSenderRole());
	    return dto;
	}

	@Override
	public Doctor registerDoctor(DoctorDTO dto) throws UserNotFoundException, DuplicateDoctorException {
	    Optional<User> userOpt = userRepo.findById(dto.getUserId());
	    if (userOpt.isEmpty()) {
	        throw new UserNotFoundException("User not found");
	    }

	    User user = userOpt.get();

	    if (user.getRole() != User.Role.DOCTOR) {
	        throw new IllegalArgumentException("User role is not DOCTOR");
	    }

	    Optional<Doctor> existingDoctor = doctorRepo.findById(dto.getUserId());
	    if (existingDoctor.isPresent()) {
	        throw new DuplicateDoctorException("Doctor already registered with this userId");
	    }

	    Doctor doctor = new Doctor();
	    doctor.setUser(user);
	    doctor.setSpecialization(dto.getSpecialization());
	    doctor.setExperienceYears(dto.getExperienceYears());
	    doctor.setQualification(dto.getQualification());
	    doctor.setDesignation(dto.getDesignation());

	    return doctorRepo.save(doctor);
	}


	@Override
	public Doctor updateDoctor(Long id, DoctorDTO dto) throws UserNotFoundException {
		Doctor existing = doctorRepo.findById(id).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

		existing.setSpecialization(dto.getSpecialization());
		existing.setExperienceYears(dto.getExperienceYears());
		existing.setQualification(dto.getQualification());
		existing.setDesignation(dto.getDesignation());

		return doctorRepo.save(existing); 
	}

	@Override
	public Doctor getDoctorById(Long doctorId) {
		return doctorRepo.findById(doctorId).orElse(null);
	}

	@Override
	public boolean deleteDoctor(Long doctorId) {
		if (doctorRepo.existsById(doctorId)) {
			doctorRepo.deleteById(doctorId);
			return true;
		}
		return false;
	}

	@Override
	public boolean checkDoctorExistsById(Long doctorId) {
		return doctorRepo.existsById(doctorId);
	}

	@Override
	public List<Doctor> getDoctorsBySpecialization(String specialization) {
		return doctorRepo.findBySpecialization(specialization);
	}

	@Override
	public List<Doctor> getDoctorsByDesignation(String designation) {
		return doctorRepo.findByDesignation(designation);
	}

	@Override
	public List<Doctor> getDoctorsByGender(String gender) {
		return doctorRepo.getDoctorsByGender(gender);
	}

	@Override
	public List<Doctor> getAllDoctors() {
		return doctorRepo.findAll();
	}

	@Override
	public boolean existsByUserUserId(Long userId) {
	    return doctorRepo.existsByUserUserId(userId);
	}

	@Override
	public Doctor getDoctorByUserId(Long userId) {
	    return doctorRepo.findByUserUserId(userId)
	            .orElseThrow(() -> new DoctorNotFoundException("Doctor not found for userId: " + userId));
	}

	@Override
	public List<Doctor> searchDoctorsByName(String name) {
		return doctorRepo.searchDoctorsByName(name);
	}

	@Override
	public List<Appointment> getAppointments(Long doctorId) {
		return AppointmentRepo.findByDoctorDoctorId(doctorId);
	}

	@Override
	public List<Appointment> getUpcomingAppointments(Long doctorId) {
		return AppointmentRepo.findByDoctorDoctorIdAndAppointmentDateAfter(doctorId, LocalDateTime.now());
	}

	@Override
	public MedicalRecord addMedicalRecord(MedicalRecordDTO medicalRecordDTO) throws AppointmentNotFoundException {

		return MedicalRecordService.createMedicalRecord(medicalRecordDTO);

	}

	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecordDTO updatedRecord)
			throws MedicalRecordNotFoundException, AppointmentNotFoundException {
		return MedicalRecordService.updateMedicalRecord(updatedRecord);

	}

	@Override
	public List<MedicalRecordDTO> getPatientMedicalHistory(Long patientId) {
		return MedicalRecordService.getMedicalRecordsByPatientId(patientId);
	}

	@Override
	public Prescription addPrescription(PrescriptionDTO prescriptionDTO) throws MedicalRecordNotFoundException {
		return PrescriptionService.createPrescription(prescriptionDTO);

	}

	@Override
	public Prescription updatePrescription(PrescriptionDTO prescriptionDTO) throws PrescriptionNotFoundException {
		return PrescriptionService.updatePrescription(prescriptionDTO);
	}
	
	@Override
	public MessageDTO sendMessage(MessageDTO messageDTO) {
	    doctorRepo.findById(messageDTO.getDoctorId());
	    patientRepository.findById(messageDTO.getPatientId())
	        .orElseThrow(() -> new NoSuchElementException("Patient not found"));

	    String url = MESSAGESERVICEURL + "/send";
	    Message message = restTemplate.postForObject(url, messageDTO, Message.class);
	    return convertToDTO(message);
	}

	@Override
	public List<MessageDTO> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId) {
	    doctorRepo.findById((long) doctorId);
	    patientRepository.findById((long) patientId)
	        .orElseThrow(() -> new NoSuchElementException("Patient not found"));

	    String url = MESSAGESERVICEURL + "/messageBetween/doctor/" + doctorId + "/patient/" + patientId;
	    Message[] messages = restTemplate.getForObject(url, Message[].class);

	    List<MessageDTO> dtoList = new ArrayList<>();
	    if (messages != null) {
	        for (Message msg : messages) {
	            dtoList.add(convertToDTO(msg));
	        }
	    }
	    return dtoList;
	}

	@Override
	public List<MessageDTO> getUnreadMessagesForDoctor(int doctorId) {
	    doctorRepo.findById((long) doctorId);

	    String url = MESSAGESERVICEURL + "/unread/doctor/" + doctorId;
	    Message[] messages = restTemplate.getForObject(url, Message[].class);

	    List<MessageDTO> dtoList = new ArrayList<>();
	    if (messages != null) {
	        for (Message msg : messages) {
	            dtoList.add(convertToDTO(msg));
	        }
	    }
	    return dtoList;
	}

	@Override
	public boolean markMessageAsRead(int messageId) {
	    try {
	        String getUrl = MESSAGESERVICEURL + "/getbyid/" + messageId;
	        Message message = restTemplate.getForObject(getUrl, Message.class);

	        if (message == null) {
	            throw new NoSuchElementException("Message not found");
	        }

	        doctorRepo.findById(message.getDoctorId());

	        String markUrl = MESSAGESERVICEURL + "/mark-read/" + messageId;
	        restTemplate.put(markUrl, null); // No request body

	        return true;
	    } catch (Exception e) {
	        log.error("Failed to mark message as read: " + e.getMessage());
	        return false;
	    }
	}

	@Override
	public List<MessageDTO> getMessagesSentByDoctor(int doctorId) {
	    doctorRepo.findById((long) doctorId);

	    String url = MESSAGESERVICEURL + "/sent/doctor/" + doctorId;
	    Message[] messages = restTemplate.getForObject(url, Message[].class);

	    List<MessageDTO> dtoList = new ArrayList<>();
	    if (messages != null) {
	        for (Message msg : messages) {
	            dtoList.add(convertToDTO(msg));
	        }
	    }
	    return dtoList;
	}


	@Override
	public List<Patient> searchPatientsByName(String name) {
		return patientRepository.findByUserNameContainingIgnoreCase(name);
	}


	@Override
	public List<Patient> searchPatientsByBloodGroup(String bloodGroup) {
		return patientRepository.findByBloodGroup(bloodGroup);
	}
}
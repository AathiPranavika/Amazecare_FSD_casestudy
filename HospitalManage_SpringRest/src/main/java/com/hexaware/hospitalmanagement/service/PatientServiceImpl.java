package com.hexaware.hospitalmanagement.service;
import java.awt.TrayIcon.MessageType;
import java.lang.System.Logger;
/**
 * Service implementation for managing patient operations in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hexaware.hospitalmanagement.DTO.AppointmentDTO;
import com.hexaware.hospitalmanagement.DTO.MedicalRecordDTO;
import com.hexaware.hospitalmanagement.DTO.MessageDTO;
import com.hexaware.hospitalmanagement.DTO.PatientDTO;
import com.hexaware.hospitalmanagement.entity.Appointment;
import com.hexaware.hospitalmanagement.entity.Doctor;
import com.hexaware.hospitalmanagement.entity.Message;
import com.hexaware.hospitalmanagement.entity.Patient;
import com.hexaware.hospitalmanagement.entity.Prescription;
import com.hexaware.hospitalmanagement.entity.User;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.exception.DuplicatePatientException;
import com.hexaware.hospitalmanagement.exception.PatientNotFoundException;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;
import com.hexaware.hospitalmanagement.repository.PatientRepository;
import com.hexaware.hospitalmanagement.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserAccessService userAccessService;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IPrescriptionService prescriptionService;

    @Autowired
    private IMedicalRecordService medicalRecordService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RestTemplate restTemplate;

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
    // Register patient
    @Override
    public Patient registerPatient(PatientDTO dto) throws DuplicatePatientException, UserNotFoundException {
        Optional<Patient> existing = patientRepository.findById(dto.getUserId());
        if (existing.isPresent()) {
            throw new DuplicatePatientException("Patient with this userId already exists");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getRole() != User.Role.PATIENT) {
            throw new IllegalArgumentException("User role is not PATIENT");
        }

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setAddress(dto.getAddress());
        patient.setEmergencyContact(dto.getEmergencyContact());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setMedicalHistory(dto.getMedicalHistory());

        return patientRepository.save(patient);
    }

    // Update patient details
    @Override
    public Patient updatePatient(Long patientId, PatientDTO dto) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NoSuchElementException("Patient not found"));

        patient.setAddress(dto.getAddress());
        patient.setEmergencyContact(dto.getEmergencyContact());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setMedicalHistory(dto.getMedicalHistory());

        return patientRepository.save(patient);
    }

    @Override
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new NoSuchElementException("Patient not found"));
    }

    // Delete patient
    @Override
    public boolean deletePatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NoSuchElementException("Patient not found"));

        patientRepository.delete(patient);
        return true;
    }

    @Override
    public boolean checkPatientExistsById(Long patientId) {
        return patientRepository.existsById(patientId);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.findByUserNameContainingIgnoreCase(name);
    }

    @Override
    public List<Patient> searchPatientsByBloodGroup(String bloodGroup) {
        return patientRepository.findByBloodGroup(bloodGroup);
    }
    @Override
    public boolean existsByUserUserId(Long id) {
        return patientRepository.existsByUserUserId(id);
    }

    @Override
    public Patient getPatientByUserId(Long userId) {
        return patientRepository.findByUserUserId(userId)
            .orElseThrow(() -> new PatientNotFoundException("Patient not found for User ID: " + userId));
    }


    // Book appointment
    @Override
    public Appointment bookAppointment(AppointmentDTO dto) throws AppointmentNotFoundException {
        return appointmentService.bookAppointment(dto);
    }

    // Get upcoming appointments
    @Override
    public List<Appointment> getUpcomingAppointments(Long patientId) {
        return appointmentService.getUpcomingAppointmentsForPatient(patientId);
    }

    // Cancel appointment
    @Override
    public boolean cancelAppointment(Long appointmentId) throws AppointmentNotFoundException {
        return appointmentService.cancelAppointmentById(appointmentId) != null;
    }

    // Get medical records by patient
    @Override
    public List<MedicalRecordDTO> getMedicalRecordsByPatientId(Long patientId) {
        return medicalRecordService.getMedicalRecordsByPatientId(patientId);
    }

    // Get prescriptions by patient
    @Override
    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionService.getPrescriptionsByPatientId(patientId);
    }

    // Get doctors by specialization (public access)
    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorService.getDoctorsBySpecialization(specialization);
    }

    // Get doctors by designation (public access)
    @Override
    public List<Doctor> getDoctorsByDesignation(String designation) {
        return doctorService.getDoctorsByDesignation(designation);
    }

    // Get doctors by gender (public access)
    @Override
    public List<Doctor> getDoctorsByGender(String gender) {
        return doctorService.getDoctorsByGender(gender);
    }

    // Get all doctors (public access)
    @Override
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // Search doctors by name (public access)
    @Override
    public List<Doctor> searchDoctorsByName(String name) {
        return doctorService.searchDoctorsByName(name);
    }

    // Send message
 // Send message
    @Override
    public MessageDTO sendMessage(MessageDTO messageDTO) {
        doctorService.getDoctorById(messageDTO.getDoctorId());

        String url = MESSAGESERVICEURL + "/send";
        Message message = restTemplate.postForObject(url, messageDTO, Message.class);
        return convertToDTO(message);
    }

    // Get messages between doctor and patient
    @Override
    public List<MessageDTO> getMessagesBetweenDoctorAndPatient(int doctorId, int patientId) {
        doctorService.getDoctorById((long) doctorId);

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

    // Get unread messages for patient
    @Override
    public List<MessageDTO> getUnreadMessagesForPatient(int patientId) {
        String url = MESSAGESERVICEURL + "/unread/patient/" + patientId;
        Message[] messages = restTemplate.getForObject(url, Message[].class);

        List<MessageDTO> dtoList = new ArrayList<>();
        if (messages != null) {
            for (Message msg : messages) {
                dtoList.add(convertToDTO(msg));
            }
        }
        return dtoList;
    }

    // Mark message as read
    @Override
    public boolean markMessageAsRead(int messageId) {
        try {
            String getByMessageIdUrl = MESSAGESERVICEURL + "/getbyid/" + messageId;
            Message message = restTemplate.getForObject(getByMessageIdUrl, Message.class);

            if (message == null) {
                throw new NoSuchElementException("Message not found");
            }

            String markUrl = MESSAGESERVICEURL + "/mark-read/" + messageId;
            restTemplate.put(markUrl, null); // No request body

            return true;
        } catch (Exception e) {
            log.error("Failed to mark message as read: " + e.getMessage());
            return false;
        }
    }

    // Get messages sent by patient
    @Override
    public List<MessageDTO> getMessagesSentByPatient(int patientId) {
        String url = MESSAGESERVICEURL + "/sent/patient/" + patientId;
        Message[] messages = restTemplate.getForObject(url, Message[].class);

        List<MessageDTO> dtoList = new ArrayList<>();
        if (messages != null) {
            for (Message msg : messages) {
                dtoList.add(convertToDTO(msg));
            }
        }
        return dtoList;
    }

	}

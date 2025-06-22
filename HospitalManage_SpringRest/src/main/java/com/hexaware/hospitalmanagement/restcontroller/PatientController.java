package com.hexaware.hospitalmanagement.restcontroller;
/**
* REST controller for patients-related operations in the Hospital Management System.
* * 
* @author Aathi Pranavika
* @version 1.0
* */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.hospitalmanagement.DTO.AppointmentDTO;
import com.hexaware.hospitalmanagement.DTO.MedicalRecordDTO;
import com.hexaware.hospitalmanagement.DTO.MessageDTO;
import com.hexaware.hospitalmanagement.DTO.PatientDTO;
import com.hexaware.hospitalmanagement.entity.Appointment;
import com.hexaware.hospitalmanagement.entity.Doctor;
import com.hexaware.hospitalmanagement.entity.Message;
import com.hexaware.hospitalmanagement.entity.Patient;
import com.hexaware.hospitalmanagement.entity.Prescription;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.exception.DuplicatePatientException;
import com.hexaware.hospitalmanagement.exception.InvalidRoleException;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;
import com.hexaware.hospitalmanagement.service.IPatientService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@CrossOrigin("http://localhost:4200")

@RestController
@RequestMapping("/api/patients")
@Slf4j
public class PatientController {

    @Autowired
    private IPatientService patientService;

    // Register patient (only PATIENT role & own userId)
    @PostMapping("/register")
    @PreAuthorize("hasRole('PATIENT') and #patientDto.userId == principal.id")
    public ResponseEntity<Patient> registerPatient(@Valid @RequestBody PatientDTO patientDto)
            throws UserNotFoundException, DuplicatePatientException, InvalidRoleException {
        log.info("Registering new patient");
        Patient saved = patientService.registerPatient(patientDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Get patient by ID (ADMIN or DOCTOR roles)
    @GetMapping("/getbyid/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or  #id == principal.id")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) throws UserNotFoundException {
        log.info("Fetching patient by ID: {}", id);
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            throw new UserNotFoundException("Patient not found with ID: " + id);
        }
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    // Update patient details (only PATIENT and own patientId)
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsPatient(principal.id, #id)")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO dto) {
        log.info("Updating patient with ID: {}", id);
        Patient updated = patientService.updatePatient(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Delete patient (only PATIENT and own patientId)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsPatient(principal.id, #id)")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) throws UserNotFoundException {
        log.info("Deleting patient with ID: {}", id);
        boolean deleted = patientService.deletePatient(id);
        if (deleted) {
            log.info("Patient deleted successfully: {}", id);
            return new ResponseEntity<>("Patient deleted successfully", HttpStatus.OK);
        } else {
            log.warn("Deletion failed for patient ID: {}", id);
            throw new UserNotFoundException("Patient not found with ID: " + id);
        }
    }

    // Get all patients (ADMIN or DOCTOR)
    @GetMapping("/getall")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public List<Patient> getAllPatients() {
        log.info("Fetching all patients");
        return patientService.getAllPatients();
    }

    // Search patients by name (ADMIN or DOCTOR)
    @GetMapping("/search/name/{name}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public List<Patient> searchPatientsByName(@PathVariable String name) {
        log.info("Searching patients by name: {}", name);
        return patientService.searchPatientsByName(name);
    }

    // Search patients by blood group (ADMIN or DOCTOR)
    @GetMapping("/search/bloodgroup/{bloodGroup}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public List<Patient> searchPatientsByBloodGroup(@PathVariable String bloodGroup) {
        log.info("Searching patients by blood group: {}", bloodGroup);
        return patientService.searchPatientsByBloodGroup(bloodGroup);
    }

 // Check if patient exists by userId (used before registering)
    @GetMapping("/exists/{userId}")
    public ResponseEntity<Boolean> checkPatientExistsByUserId(@PathVariable Long userId) {
        log.info("Checking if patient exists for userId: {}", userId);
        boolean exists = patientService.existsByUserUserId(userId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    //getting patient details by userId
    @GetMapping("/byuserId/{userId}")
    public ResponseEntity<Patient> getPatientByUserId(@PathVariable Long userId) {
        Patient patient = patientService.getPatientByUserId(userId);
        return ResponseEntity.ok(patient);
    }

    // Book appointment (only PATIENT and own patientId)
    @PostMapping("/bookAppointment")
    @PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsPatient(principal.id, #dto.patientId)")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody AppointmentDTO dto)
            throws AppointmentNotFoundException {
        log.info("Booking appointment for patientId: {}", dto.getPatientId());
        Appointment appointment = patientService.bookAppointment(dto);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    // Cancel appointment (only PATIENT and owns appointment)
    @PutMapping("/cancelAppointment/{id}")
    @PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsAppointment(principal.id, #id)")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        log.info("Cancelling appointment with ID: {}", id);
        boolean cancelled = patientService.cancelAppointment(id);
        if (cancelled) {
            log.info("Appointment cancelled successfully: {}", id);
            return new ResponseEntity<>("Appointment cancelled successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to cancel appointment", HttpStatus.BAD_REQUEST);
        }
    }

    // Get upcoming appointments (only PATIENT and own patientId)
    @GetMapping("/appointments/upcoming/{patientId}")
    @PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsPatient(principal.id, #patientId)")
    public List<Appointment> getUpcomingAppointments(@PathVariable Long patientId) {
        log.info("Fetching upcoming appointments for patientId: {}", patientId);
        return patientService.getUpcomingAppointments(patientId);
    }

    // Get medical records by patient (only PATIENT and own patientId)
    @GetMapping("/medicalRecords/{patientId}")
    @PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsPatient(principal.id, #patientId)")
    public List<MedicalRecordDTO> getMedicalRecordsByPatientId(@PathVariable Long patientId) {
        log.info("Fetching medical records for patientId: {}", patientId);
        return patientService.getMedicalRecordsByPatientId(patientId);
    }

    // Get prescriptions by patient (only PATIENT and own patientId)
    @GetMapping("/prescriptions/{patientId}")
    @PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsPatient(principal.id, #patientId)")
    public List<Prescription> getPrescriptionsByPatientId(@PathVariable Long patientId) {
        log.info("Fetching prescriptions for patientId: {}", patientId);
        return patientService.getPrescriptionsByPatientId(patientId);
    }

    // Get doctors by specialization (public access, no role restriction)
    @GetMapping("/doctors/specialization/{specialization}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String specialization) {
        log.info("Fetching doctors by specialization: {}", specialization);
        return patientService.getDoctorsBySpecialization(specialization);
    }

    // Get doctors by designation (public access)
    @GetMapping("/doctors/designation/{designation}")
    public List<Doctor> getDoctorsByDesignation(@PathVariable String designation) {
        log.info("Fetching doctors by designation: {}", designation);
        return patientService.getDoctorsByDesignation(designation);
    }

    // Get doctors by gender (public access)
    @GetMapping("/doctors/gender/{gender}")
    public List<Doctor> getDoctorsByGender(@PathVariable String gender) {
        log.info("Fetching doctors by gender: {}", gender);
        return patientService.getDoctorsByGender(gender);
    }

    // Get all doctors (public access)
    @GetMapping("/doctors/getAll")
    public List<Doctor> getAllDoctors() {
        log.info("Fetching all doctors");
        return patientService.getAllDoctors();
    }

    // Search doctors by name (public access)
    @GetMapping("/doctors/search/{name}")
    public List<Doctor> searchDoctorsByName(@PathVariable String name) {
        log.info("Searching doctors by name: {}", name);
        return patientService.searchDoctorsByName(name);
    }

 // Send message (only PATIENT and owns patientId)
    @PostMapping("/sendMessage")
   @PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsPatient(principal.id, #messageDTO.patientId)")
    public MessageDTO sendMessage(@RequestBody MessageDTO messageDTO) {
        log.info("Sending message from patientId: {}", messageDTO.getPatientId());
        return patientService.sendMessage(messageDTO);
    }

    // Get messages between doctor and patient (only PATIENT owns patientId)
    
    @GetMapping("/messages/between/{doctorId}/{patientId}")
   // @PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsPatient(principal.id, #patientId)")
    public List<MessageDTO> getMessagesBetweenDoctorAndPatient(@PathVariable int doctorId, @PathVariable int patientId) {
        log.info("Fetching messages between doctorId: {} and patientId: {}", doctorId, patientId);
        return patientService.getMessagesBetweenDoctorAndPatient(doctorId, patientId);
    }

    // Get unread messages for patient (only PATIENT owns patientId)
    
    @GetMapping("/messages/unread/{patientId}")
    @PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsPatient(principal.id, #patientId)")
    public List<MessageDTO> getUnreadMessagesForPatient(@PathVariable int patientId) {
        log.info("Fetching unread messages for patientId: {}", patientId);
        return patientService.getUnreadMessagesForPatient(patientId);
    }

    // Mark message as read (only PATIENT who owns the message)
    
    @PutMapping("/messages/markAsRead/{messageId}")
    //@PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsMessage(principal.id, #messageId)")
    @PreAuthorize("hasRole('PATIENT')")
    public String markMessageAsRead(@PathVariable int messageId) {
        log.info("Marking message as read: {}", messageId);
        boolean updated = patientService.markMessageAsRead(messageId);
        return updated ? "Message marked as read" : "Message not found or already read";
    }

    // Get messages sent by patient (only PATIENT owns patientId)
    @GetMapping("/messages/sentByPatient/{patientId}")
    @PreAuthorize("hasRole('PATIENT') and @userAccessService.userOwnsPatient(principal.id, #patientId)")
    public List<MessageDTO> getMessagesSentByPatient(@PathVariable int patientId) {
        log.info("Fetching messages sent by patientId: {}", patientId);
        return patientService.getMessagesSentByPatient(patientId);
    }

}

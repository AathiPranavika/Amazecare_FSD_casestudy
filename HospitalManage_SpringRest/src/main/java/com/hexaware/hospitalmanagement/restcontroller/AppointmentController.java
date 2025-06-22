package com.hexaware.hospitalmanagement.restcontroller;
import java.lang.System.Logger;
/**
* REST controller for appointments-related operations in the Hospital Management System.
* * 
* @author Aathi Pranavika
* @version 1.0
* */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.hospitalmanagement.DTO.AppointmentDTO;
import com.hexaware.hospitalmanagement.entity.Appointment;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.service.IAppointmentService;

import jakarta.validation.Valid;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
@CrossOrigin("http://localhost:4200")

@RestController
@RequestMapping("/api/appointments")
@Slf4j
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<Appointment> bookAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) throws AppointmentNotFoundException {
        log.info("Booking appointment for patientId: {} with doctorId: {}", appointmentDTO.getPatientId(), appointmentDTO.getDoctorId());
        Appointment savedAppointment = appointmentService.bookAppointment(appointmentDTO);
        log.info("Appointment booked successfully with id: {}", savedAppointment.getAppointmentId());
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        log.info("Fetching all appointments");
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(allAppointments, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) throws AppointmentNotFoundException {
        log.info("Fetching appointment by id: {}", id);
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public Appointment updateAppointment(@PathVariable Long id,
    		@Valid @RequestBody AppointmentDTO appointmentDTO) throws AppointmentNotFoundException {
        log.info("Updating appointment id: {}", id);
        return appointmentService.updateAppointment(id, appointmentDTO);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        log.info("Cancelling appointment id: {}", id);
        Appointment appointment = appointmentService.cancelAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }


    @PutMapping("/reject/{id}")
    public ResponseEntity<Appointment> rejectAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        log.info("Rejecting appointment id: {}", id);
        Appointment appointment = appointmentService.rejectAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<Appointment> completeAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        log.info("Completing appointment id: {}", id);
        Appointment appointment = appointmentService.completeAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PutMapping("/confirm/{id}/{dateTime}")
    public Appointment confirmAppointment(@PathVariable Long id,
                                          @RequestBody AppointmentDTO appointmentDTO,
                                          @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) throws AppointmentNotFoundException {
        log.info("Confirming appointment id: {} for datetime: {}", id, dateTime);
        return appointmentService.confirmAppointment(id, appointmentDTO, dateTime);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsByPatientId(@PathVariable Long patientId) {
        log.info("Fetching appointments for patientId: {}", patientId);
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
        log.info("Fetching appointments for doctorId: {}", doctorId);
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }

    @GetMapping("/doctor/{doctorId}/upcoming")
    public List<Appointment> getUpcomingAppointmentsForDoctor(@PathVariable Long doctorId) {
        log.info("Fetching upcoming appointments for doctorId: {}", doctorId);
        return appointmentService.getUpcomingAppointmentsForDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}/upcoming")
    public List<Appointment> getUpcomingAppointmentsForPatient(@PathVariable Long patientId) {
        log.info("Fetching upcoming appointments for patientId: {}", patientId);
        return appointmentService.getUpcomingAppointmentsForPatient(patientId);
    }

    @GetMapping("/status/{status}")
    public List<Appointment> getAppointmentsByStatus(@PathVariable Appointment.AppointmentStatus status) {
        log.info("Fetching appointments by status: {}", status);
        return appointmentService.getAppointmentsByStatus(status);
    }

    @GetMapping("/patient/{patientId}/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientAndStatus(
            @PathVariable Long patientId,
            @PathVariable Appointment.AppointmentStatus  status) {
        List<Appointment> result = appointmentService.getAppointmentsByPatientAndStatus(patientId, status);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/doctor/{doctorId}/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorAndStatus(
            @PathVariable Long doctorId,
            @PathVariable Appointment.AppointmentStatus  status) {
        List<Appointment> result = appointmentService.getAppointmentsByDoctorAndStatus(doctorId, status);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/{date}")
    public List<Appointment> searchAppointmentsByDate(@PathVariable
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
        log.info("Searching appointments by date: {}", date);
        return appointmentService.searchAppointmentsByDate(date);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) throws AppointmentNotFoundException {
        log.info("Deleting appointment with id: {}", id);
        appointmentService.deleteAppointmentById(id);
        return new ResponseEntity<>("Appointment with ID " + id + " deleted successfully.", HttpStatus.OK);
    }
    
    @GetMapping("/patient/{patientId}/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByPatientAndDoctor(@PathVariable Long patientId, @PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByPatientAndDoctor(patientId, doctorId);
    }

}

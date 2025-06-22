package com.hexaware.HospitalManagement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.hexaware.hospitalmanagement.DTO.AppointmentDTO;
import com.hexaware.hospitalmanagement.entity.Appointment;
import com.hexaware.hospitalmanagement.entity.Appointment.AppointmentStatus;
import com.hexaware.hospitalmanagement.exception.AppointmentNotFoundException;
import com.hexaware.hospitalmanagement.service.IAppointmentService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(OrderAnnotation.class)
class AppointmentServiceImplTest {

    @Autowired
    private IAppointmentService appointmentService;


    @Test
    @Order(1)
    void testBookAppointment() throws AppointmentNotFoundException {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setPatientId(1L);
        dto.setDoctorId(1L);
        dto.setSymptoms("Headache and fever");

        Appointment saved = appointmentService.bookAppointment(dto);
        assertNotNull(saved);
        assertEquals(AppointmentStatus.PENDING, saved.getStatus());

    }

    @Test
    @Order(2)
    void testConfirmAppointment() throws AppointmentNotFoundException {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentId(2L);
        LocalDateTime confirmedDate = LocalDateTime.now().plusDays(1);

        Appointment confirmed = appointmentService.confirmAppointment(2L, dto, confirmedDate);
        assertEquals(AppointmentStatus.CONFIRMED, confirmed.getStatus());
        assertEquals(confirmedDate, confirmed.getAppointmentDate());
    }

    @Test
    @Order(3)
    void testUpdateAppointment() throws AppointmentNotFoundException {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentDate(LocalDateTime.now().plusDays(2));
        dto.setSymptoms("Updated symptoms");
        dto.setStatus(AppointmentStatus.CONFIRMED);

        Appointment updated = appointmentService.updateAppointment(2L, dto);
        assertEquals("Updated symptoms", updated.getSymptoms());
    }

    @Test
    @Order(4)
    void testGetAppointmentById() throws AppointmentNotFoundException {
        Appointment appointment = appointmentService.getAppointmentById(2L);
        assertNotNull(appointment);
        assertEquals(2, appointment.getAppointmentId());
    }

    @Test
    @Order(5)
    void testGetAppointmentsByStatus() {
        List<Appointment> confirmedList = appointmentService.getAppointmentsByStatus(AppointmentStatus.PENDING);
        assertNotNull(confirmedList);
    }

    @Test
    @Order(6)
    void testCancelAppointment() throws AppointmentNotFoundException {
        Appointment cancelled = appointmentService.cancelAppointmentById(2L);
        assertEquals(AppointmentStatus.CANCELLED, cancelled.getStatus());
    }

    @Test
    @Order(7)
    void testDeleteAppointment() throws AppointmentNotFoundException {
        appointmentService.deleteAppointmentById(2L);
        Exception ex = assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.getAppointmentById(2L);
        });
        assertEquals("Appointment not found", ex.getMessage());
    }
}

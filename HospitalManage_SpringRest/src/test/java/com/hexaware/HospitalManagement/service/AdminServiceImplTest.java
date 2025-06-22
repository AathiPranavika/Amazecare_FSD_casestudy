package com.hexaware.HospitalManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.hexaware.hospitalmanagement.DTO.AdminDTO;
import com.hexaware.hospitalmanagement.entity.Admin;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;
import com.hexaware.hospitalmanagement.service.IAdminService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback
@TestMethodOrder(OrderAnnotation.class)
class AdminServiceImplTest {

    @Autowired
    private IAdminService adminService;

    @Test
    @Order(1)
    void testRegisterAdmin() throws  UserNotFoundException {
        AdminDTO dto = new AdminDTO();
        dto.setUserId(33L);
        dto.setAdminCode("ADM101");
        dto.setDepartment("System management");
        dto.setQualification("MBA");

        Admin savedAdmin = adminService.registerAdmin(dto);
        assertNotNull(savedAdmin);
        assertEquals("MBA", savedAdmin.getQualification());
    }

    @Test
    @Order(2)
    void testUpdateAdmin() throws UserNotFoundException {
        AdminDTO updateDto = new AdminDTO();
        updateDto.setDepartment("Updated Department");
        updateDto.setUserId(1L);
        Admin updated = adminService.updateAdmin(1L, updateDto);
        assertNotNull(updated);
        assertEquals("Updated Department", updated.getDepartment());
    }

    @Test
    @Order(3)
    void testGetAdminById() {
        Admin admin = adminService.getAdminById(1L);
        assertNotNull(admin);
    }

    @Test
    @Order(4)
    void testGetAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        assertNotNull(admins);
        assertFalse(admins.isEmpty());
    }

   
    @Test
    @Order(6)
    void testDeleteAdmin() {
        boolean deleted = adminService.deleteAdmin(1L);
        assertTrue(deleted);
    }
}

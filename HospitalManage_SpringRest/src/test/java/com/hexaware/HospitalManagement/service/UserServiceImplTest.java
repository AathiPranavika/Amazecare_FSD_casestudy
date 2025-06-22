package com.hexaware.HospitalManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.hexaware.hospitalmanagement.DTO.UserDTO;
import com.hexaware.hospitalmanagement.entity.User;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;
import com.hexaware.hospitalmanagement.service.IUserService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback
class UserServiceImplTest {
	
	@Autowired
	IUserService userService;


	@Test
	@Order(1)
	void testRegisterUser() {
		UserDTO dto = new UserDTO();
		dto.setName("John Doe");
		dto.setEmail("johndoe@gmail.com");
		dto.setPassword("secret123");
		dto.setRole(User.Role.PATIENT);
		dto.setGender("Male");
		dto.setDateOfBirth(LocalDate.of(1990, 1, 1));
		dto.setContactNumber("9999999999");
		String user=userService.registerUser(dto);
		
		assertNotNull(user);
		

	}

	@Order(3)
	@Test
	void testGetUserById() throws UserNotFoundException {
		User user=userService.getUserById(23L);
		assertNotNull(user);
	}

	@Order(4)
	@Test
	void testGetUserByEmail() throws UserNotFoundException {
		User user=userService.getUserByEmail("john.doe@example.com");
		assertNotNull(user);
	}

	@Order(2)
	@Test
	void testUpdateUser() throws UserNotFoundException {
		UserDTO dto = new UserDTO(23L,"Johny", "johndoe@gmail.com", "secret123", User.Role.PATIENT, "Male", LocalDate.of(1990, 1, 1), "9999999999");
		User user=userService.updateUser(23L, dto);
		assertEquals("Johny",user.getName());
	}

	@Order(5)
	@Test
	void testDeleteUser() throws UserNotFoundException {
		assertTrue(userService.deleteUser(23L));
		
	}

	@Test
	void testGetAllUsers() {
		List<User> userList=userService.getAllUsers();
		assertTrue(userList.size()>0);
	}

	@Test
	void testGetUsersByRole() {
		List<User> userList=userService.getUsersByRole(User.Role.DOCTOR);
		assertTrue(userList.size()>0);
	}

}

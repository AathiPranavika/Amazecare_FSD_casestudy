package com.hexaware.hospitalmanagement.restcontroller;
/**
* REST controller for users-related operations in the Hospital Management System.
* * 
* @author Aathi Pranavika
* @version 1.0
* */
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.hospitalmanagement.DTO.UserDTO;
import com.hexaware.hospitalmanagement.entity.User;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;
import com.hexaware.hospitalmanagement.service.IUserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@CrossOrigin("http://localhost:4200")

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUser() {
        log.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        log.info("Total users fetched: {}", users.size());
        return users;
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public User getUserById(@PathVariable Long id) throws UserNotFoundException {
        log.info("Fetching user with ID: {}", id);
        User user = userService.getUserById(id);
        log.info("User fetched successfully: {}", user.getEmail());
        return user;
    }

    @GetMapping("/getByRole/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUserByRole(@PathVariable User.Role role) {
        log.info("Fetching users with role: {}", role);
        List<User> users = userService.getUsersByRole(role);
        log.info("Total users with role {}: {}", role, users.size());
        return users;
    }

    @GetMapping("/getByEmail/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        log.info("Fetching user by email: {}", email);
        User user = userService.getUserByEmail(email);
        log.info("User fetched successfully: {}", user.getUserId());
        return user;
    }

    //@PostMapping("/register user")
    public String addUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Registering new user with email: {}", userDTO.getEmail());
        return userService.registerUser(userDTO);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) throws UserNotFoundException {
        log.info("Updating user with ID: {}", id);
        User updatedUser = userService.updateUser(id, userDTO);
        log.info("User updated successfully: {}", updatedUser.getEmail());
        return updatedUser;
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public String deleteUser(@PathVariable Long id) throws UserNotFoundException {
        log.info("Deleting user with ID: {}", id);
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            log.info("User deleted successfully with ID: {}", id);
            return "User deleted successfully";
        } else {
            log.warn("Failed to delete user with ID: {}", id);
            return "Invalid user";
        }
    }
    
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> body) throws UserNotFoundException {
        String email = body.get("email");
        String newPassword = body.get("newPassword");

        boolean updated = userService.resetPasswordByEmail(email, newPassword);
        if (updated) {
            return ResponseEntity.ok("Password updated");
        }
        else {
			throw new UserNotFoundException("User not found!!Reset failed");
		}
    }
}

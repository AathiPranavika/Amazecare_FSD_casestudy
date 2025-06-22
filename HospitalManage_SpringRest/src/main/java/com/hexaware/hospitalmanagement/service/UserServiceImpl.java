package com.hexaware.hospitalmanagement.service;
/**
 * Service implementation for managing user operations in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.hospitalmanagement.DTO.UserDTO;
import com.hexaware.hospitalmanagement.entity.User;
import com.hexaware.hospitalmanagement.exception.UserNotFoundException;
import com.hexaware.hospitalmanagement.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public String registerUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setGender(userDTO.getGender());
        user.setDateOfBirth(userDTO.getDateOfBirth());  
        user.setContactNumber(userDTO.getContactNumber()); 
        userRepo.save(user);
        return "User registered successfully with user Id:"+user.getUserId();
    }


    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isPresent()) {
            return userOpt.get();      
        }        else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            throw new UserNotFoundException("User not found with email: " + email);
        }
    }


    @Override
    public User updateUser(Long userId, UserDTO userDTO) throws UserNotFoundException {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        User existingUser = optionalUser.get();
        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(userDTO.getRole());
        existingUser.setGender(userDTO.getGender());
        existingUser.setDateOfBirth(userDTO.getDateOfBirth());  
        existingUser.setContactNumber(userDTO.getContactNumber());
        return userRepo.save(existingUser);
    }

    @Override
    public boolean deleteUser(Long userId) throws UserNotFoundException {
        if (!userRepo.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        userRepo.deleteById(userId);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<User> getUsersByRole(User.Role role) {
        return userRepo.findByRole(role);
    }
    
    public boolean resetPasswordByEmail(String email, String newPassword) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword)); 
            userRepo.save(user);
            return true;
        } else {
            return false;
        }
    }
}

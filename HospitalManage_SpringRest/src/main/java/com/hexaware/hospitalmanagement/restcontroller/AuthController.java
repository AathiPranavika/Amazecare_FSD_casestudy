package com.hexaware.hospitalmanagement.restcontroller;
import java.util.HashMap;
import java.util.Map;

/**
* REST controller for authentication-related operations in the Hospital Management System.
* * 
* @author Aathi Pranavika
* @version 1.0
* */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.hospitalmanagement.DTO.AuthRequestDTO;
import com.hexaware.hospitalmanagement.DTO.UserDTO;
import com.hexaware.hospitalmanagement.service.IUserService;
import com.hexaware.hospitalmanagement.service.JwtService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/users")
public class AuthController {

	@Autowired
	IUserService service;

	@Autowired
	JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/registration/new")
	public String addNewUser(@RequestBody UserDTO userInfo) {
		return service.registerUser(userInfo);
	}

	@PostMapping("/login/authenticate")
	public ResponseEntity<Map<String, String>> authenticateAndGetToken(@RequestBody AuthRequestDTO authRequest) {
	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

	    if (authentication.isAuthenticated()) {
	        String token = jwtService.generateToken(authRequest.getUserName());
	        logger.info("Token :"+ token);

	        Map<String, String> response = new HashMap<>();
	        response.put("token", token);
	        return ResponseEntity.ok(response);
	    } else {
	        throw new UsernameNotFoundException("Invalid username or password");
	    }
	}


}

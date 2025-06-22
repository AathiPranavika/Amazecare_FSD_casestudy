package com.hexaware.hospitalmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class HospitalManageSpringRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalManageSpringRestApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplateBuilder()
	        .interceptors((request, body, execution) -> {
	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	            if (authentication != null) {
	                Object credentials = authentication.getCredentials();
	                log.info("Credentials from SecurityContext: " + credentials);

	                if (credentials instanceof String token) {
	                    request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
	                    log.info(" Added token to header: Bearer " + token);
	                } else {
	                	log.info(" Credentials not instance of String (actual: " + credentials.getClass() + ")");
	                }
	            } else {
	            	log.info(" No authentication in SecurityContext");
	            }

	            return execution.execute(request, body);
	        })
	        .build();
	}
	
    
	

}

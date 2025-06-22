package com.hexaware.hospitalmanagement.filter;
/**
 * Filter that intercepts incoming HTTP requests once per request to validate JWT tokens.
* @author Aathi Pranavika
* @version 1.0
* */
import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hexaware.hospitalmanagement.config.CustomUserDetailsService;
import com.hexaware.hospitalmanagement.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		log.info("JwtAuthFilter is processing the request: " + request.getRequestURI());

		  String authHeader = request.getHeader("Authorization");
		  log.info("Authorization header: " + authHeader);
	        String token = null;
	        String username = null;

	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            token = authHeader.substring(7);
	            username = jwtService.extractUserName(token);
	            log.info("Extracted username: " + username);
	        }


	        
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	            if (jwtService.validateToken(token, userDetails)) {
	            	log.info("Authenticated user: " + userDetails.getUsername());
	                log.info("Authorities: " + userDetails.getAuthorities());
	               
	            
	                //it actually creates a Spring Security Authentication object that represents a fully authenticated user.
	                UsernamePasswordAuthenticationToken authToken =
	                    new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());

	            	 log.info("Authenticated user: " + userDetails.getUsername());
	            	 log.info("Authorities: " + userDetails.getAuthorities());
    
	            	  //This adds extra info about the request, such as:IP address,Session ID
	            	authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            	
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }
	        filterChain.doFilter(request, response);
	    
	}
	
	//the JWT filter will not run for these request.
	 
	 @Override
	 protected boolean shouldNotFilter(HttpServletRequest request) {
	     String path = request.getServletPath();
	     log.info("Checking path for skipping JWT filter: " + path);
	     boolean skip = path.equals("/api/users/login/authenticate") ||
	                    path.equals("/api/users/registration/new") ||
	                    path.startsWith("/swagger-ui") ||
	                    path.startsWith("/v3/api-docs") ||
	                    path.equals("/swagger-ui.html");
	     log.info("Skip JWT filter? " + skip);
	     return skip;
	 }

	
}

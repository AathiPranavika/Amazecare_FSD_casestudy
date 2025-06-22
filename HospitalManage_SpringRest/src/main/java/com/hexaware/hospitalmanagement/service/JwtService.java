package com.hexaware.hospitalmanagement.service;
/**
 * Service responsible for generating, parsing, and validating JWT tokens.
 * Used for secure authentication and authorization in the Hospital Management System.
 * 
 * @author Aathi Pranavika G S
 * @version 1.0
 */
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hexaware.hospitalmanagement.entity.User;
import com.hexaware.hospitalmanagement.entity.User.Role;
import com.hexaware.hospitalmanagement.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Autowired
    private UserRepository userRepository;

    // Use a 256-bit (32 bytes) Base64-encoded secret key.
    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    // Generate JWT token with email as subject, role as claim
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Role role = user.getRole();
            if (role != null) {
                claims.put("role", role.name());
            }
            claims.put("id", user.getUserId());
        } else {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        return createToken(claims, email);
    }

    // Create the token with claims and subject (email)
    private String createToken(Map<String, Object> claims, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 24 * 60 * 60 * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Decode and get the signing key from the Base64-encoded SECRET
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractUserName(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract expiration date from JWT token
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Extract role claim from JWT token
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractUserName(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


}

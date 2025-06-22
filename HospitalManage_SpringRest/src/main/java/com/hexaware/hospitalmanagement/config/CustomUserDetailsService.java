package com.hexaware.hospitalmanagement.config;
/**
 * class that defines CustomUserDetailsService by implementing UserDetailsService
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexaware.hospitalmanagement.entity.User;
import com.hexaware.hospitalmanagement.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepository.findByEmail(username);

        if (userInfo.isPresent()) {
            User user = userInfo.get();
            return new  CustomUserDetails(user);
        } else {
            throw new UsernameNotFoundException("user not found " + username);
        }
    }
}


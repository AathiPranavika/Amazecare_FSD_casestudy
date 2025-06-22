package com.hexaware.hospitalmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.hospitalmanagement.entity.User;
import com.hexaware.hospitalmanagement.entity.User.Role;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//You can ask the Optional:if have a value? return it,if not found give orElse() part
	public Optional<User> findByEmail(String email);
	
	public List<User> findByRole(Role role);
	


}

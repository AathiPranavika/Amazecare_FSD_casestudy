package com.hexaware.hospitalmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.hospitalmanagement.entity.Admin;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	boolean existsByUserUserId(Long userId);
    Optional<Admin> findByUserUserId(Long userId);
}

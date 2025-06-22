package com.hexaware.hospitalmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.hospitalmanagement.entity.Patient;
import com.hexaware.hospitalmanagement.entity.User;
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    boolean existsByUser(User user);
  
    boolean existsByUserUserId(long userId);
    
    Optional<Patient> findByUserUserId(Long userId);
    
	@Query("select p from Patient p where p.user.name=:name")
	List<Patient> searchPatientsByName(String name);
	
	List<Patient> findByBloodGroup(String bloodGroup);

	List<Patient> findByUserNameContainingIgnoreCase(String name);

	boolean existsByPatientIdAndUserUserId(Long patientId, Long userId);
}

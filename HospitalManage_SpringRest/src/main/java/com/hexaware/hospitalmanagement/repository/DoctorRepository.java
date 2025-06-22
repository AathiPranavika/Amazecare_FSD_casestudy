package com.hexaware.hospitalmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.hospitalmanagement.entity.Doctor;
import com.hexaware.hospitalmanagement.entity.User;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	List<Doctor> findBySpecialization(String specialization);
	
	List<Doctor> findByDesignation(String designation);
	
    boolean existsByUser(User user);

	//getting data in user table
	@Query("select d from Doctor d where d.user.gender=?1")
	List<Doctor> getDoctorsByGender(String gender);
	
	@Query("select d from Doctor d where d.user.name=:name")
	List<Doctor> searchDoctorsByName(@Param("name") String name);

	boolean existsByDoctorIdAndUserUserId(Long doctorId, Long userId);
	
	 boolean existsByUserUserId(Long userId);
	 
	 Optional<Doctor> findByUserUserId(Long userId);

}

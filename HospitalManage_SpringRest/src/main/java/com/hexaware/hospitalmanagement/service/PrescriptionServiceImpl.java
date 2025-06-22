package com.hexaware.hospitalmanagement.service;
import java.util.ArrayList;
/**
 * Service implementation for managing prescription operations in the Hospital Management System.
 * @author Aathi Pranavika
 * @version 1.0
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hospitalmanagement.DTO.PrescriptionDTO;
import com.hexaware.hospitalmanagement.entity.MedicalRecord;
import com.hexaware.hospitalmanagement.entity.Prescription;
import com.hexaware.hospitalmanagement.exception.MedicalRecordNotFoundException;
import com.hexaware.hospitalmanagement.exception.PrescriptionNotFoundException;
import com.hexaware.hospitalmanagement.repository.MedicalRecordRepository;
import com.hexaware.hospitalmanagement.repository.PrescriptionRepository;

@Service
public class PrescriptionServiceImpl implements IPrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    @Autowired
    private MedicalRecordRepository medicalRecordRepo;

    private PrescriptionDTO convertToDTO(Prescription prescription) {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setPrescriptionId(prescription.getPrescriptionId());
        dto.setMedicalRecordId(prescription.getMedicalRecord().getRecordId()); 
        dto.setMedicineName(prescription.getMedicineName());
        dto.setDosage(prescription.getDosage());
        dto.setRemarks(prescription.getRemarks());
        return dto;
    }

    @Override
    public Prescription createPrescription(PrescriptionDTO dto) throws MedicalRecordNotFoundException {
        Optional<MedicalRecord> recordOpt = medicalRecordRepo.findById(dto.getMedicalRecordId());

        if (!recordOpt.isPresent()) {
            throw new MedicalRecordNotFoundException("Medical record not found with ID: " + dto.getMedicalRecordId());
        }

        Prescription prescription = new Prescription();
        prescription.setMedicalRecord(recordOpt.get());
        prescription.setMedicineName(dto.getMedicineName());
        prescription.setDosage(dto.getDosage());
        prescription.setRemarks(dto.getRemarks());

        return prescriptionRepo.save(prescription);
    }

    @Override
    public PrescriptionDTO getPrescriptionById(Long id) throws PrescriptionNotFoundException {
        Prescription prescription = prescriptionRepo.findById(id)
            .orElseThrow(() -> new PrescriptionNotFoundException("Prescription not found with ID: " + id));

        return convertToDTO(prescription);
    }


    @Override
    public Prescription updatePrescription(PrescriptionDTO dto) throws PrescriptionNotFoundException {
    	Prescription existing = prescriptionRepo.findById(dto.getPrescriptionId())
    		    .orElseThrow(() -> new PrescriptionNotFoundException("Prescription not found for ID: " + dto.getPrescriptionId()));

    		existing.setMedicineName(dto.getMedicineName());
    		existing.setDosage(dto.getDosage());
    		existing.setRemarks(dto.getRemarks());

    		return prescriptionRepo.save(existing); 
    }

    @Override
    public boolean deletePrescription(Long prescriptionId) throws PrescriptionNotFoundException {
        if (!prescriptionRepo.existsById(prescriptionId)) {
            throw new PrescriptionNotFoundException("Prescription not found with ID: " + prescriptionId);
        }
        prescriptionRepo.deleteById(prescriptionId);
        return true;
    }

    @Override
    public List<Prescription> getPrescriptionsByPatientId(Long patientId) {
        return prescriptionRepo.findByMedicalRecordAppointmentPatientPatientId(patientId);
    }

    @Override
    public List<PrescriptionDTO> getPrescriptionsByAppointmentId(Long appointmentId) {
        List<Prescription> prescriptions = prescriptionRepo.findByMedicalRecordAppointmentAppointmentId(appointmentId);
        List<PrescriptionDTO> dtoList = new ArrayList<>();

        for (Prescription prescription : prescriptions) {
            PrescriptionDTO dto = convertToDTO(prescription);
            dtoList.add(dto);
        }

        return dtoList;
    }


    
}

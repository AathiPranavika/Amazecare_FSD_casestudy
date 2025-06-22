package com.hexaware.hospitalmanagement.service;
import com.hexaware.hospitalmanagement.DTO.PrescriptionDTO;
import com.hexaware.hospitalmanagement.entity.Prescription;
import com.hexaware.hospitalmanagement.exception.MedicalRecordNotFoundException;
import com.hexaware.hospitalmanagement.exception.PrescriptionNotFoundException;

import java.util.List;

public interface IPrescriptionService {

    Prescription createPrescription(PrescriptionDTO prescriptionDTO) throws MedicalRecordNotFoundException;

    PrescriptionDTO getPrescriptionById(Long prescriptionId) throws PrescriptionNotFoundException;

    Prescription updatePrescription(PrescriptionDTO prescriptionDTO) throws PrescriptionNotFoundException;

    boolean deletePrescription(Long prescriptionId) throws PrescriptionNotFoundException;

    List<Prescription> getPrescriptionsByPatientId(Long patientId);

    List<PrescriptionDTO> getPrescriptionsByAppointmentId(Long appointmentId);
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PrescriptionService } from 'src/app/service/prescription.service';
import { MedicalrecordsService } from 'src/app/service/medicalrecords.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-add-prescription',
  templateUrl: './add-prescription.component.html',
  styleUrls: ['./add-prescription.component.css']
})
export class AddPrescriptionComponent implements OnInit {

  appointmentId: number = 0;
  medicalRecordId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private prescriptionService: PrescriptionService,
    private medicalService: MedicalrecordsService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.appointmentId = Number(this.route.snapshot.paramMap.get('appointmentId'));
    this.fetchMedicalRecordId(this.appointmentId);
  }

  fetchMedicalRecordId(appointmentId: number): void {
    this.medicalService.getMedicalRecordByAppointmentId(appointmentId).subscribe({
      next: (record) => {
        this.medicalRecordId = record.recordId;
        console.log("Fetched MedicalRecordID:", this.medicalRecordId);
      },
      error: (err) => {
        console.error('No medical record found for appointment:', err);
        alert('Medical record not found. Cannot add prescription.');
        this.location.back();
      }
    });
  }

  goBack()
  {
    this.location.back();
  }

  savePrescription(formData: any): void {
    formData.medicalRecordId = this.medicalRecordId;

    this.prescriptionService.addPrescription(formData).subscribe({
      next: () => {
        alert('Prescription added successfully!');
        this.location.back();
      },
      error: (err) => {
        console.error('Error adding prescription:', err);
      }
    });
  }
}

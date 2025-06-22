import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PrescriptionService } from 'src/app/service/prescription.service';
import { Location } from '@angular/common';
import { Prescription } from 'src/app/model/Prescription';

@Component({
  selector: 'app-update-prescription',
  templateUrl: './update-prescription.component.html',
  styleUrls: ['./update-prescription.component.css']
})
export class UpdatePrescriptionComponent implements OnInit {

  prescription: Prescription = {
    prescriptionId: 0,
    medicalRecordId: 0,
    medicineName: '',
    dosage: '',
    remarks: ''
  };

  constructor(
    private route: ActivatedRoute,
    private prescriptionService: PrescriptionService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    const prescId = Number(this.route.snapshot.paramMap.get('prescriptionId'));
    if (prescId) {
      this.prescriptionService.getPrescriptionById(prescId).subscribe({
        next: (data) => {
          this.prescription = data;
        },
        error: () => {
          alert('Prescription not found.');
          this.router.navigate(['/doctor-dashboard']);
        }
      });
    }
  }

  onSubmit(): void {
    this.prescriptionService.updatePrescription(this.prescription).subscribe({
      next: () => {
        alert("Prescription updated successfully!");
        this.location.back();
      },
      error: (err) => {
        console.error("Update failed:", err);
        alert("Failed to update prescription.");
      }
    });
  }

  goBack(): void {
    this.location.back();
  }
}

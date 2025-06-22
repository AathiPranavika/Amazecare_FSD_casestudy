import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Prescription } from 'src/app/model/Prescription';
import { PrescriptionService } from 'src/app/service/prescription.service';
import {Location} from '@angular/common';
@Component({
  selector: 'app-manage-prescription',
  templateUrl: './manage-prescription.component.html',
  styleUrls: ['./manage-prescription.component.css']
})
export class ManagePrescriptionComponent implements OnInit {
  
  appointmentId!: number;
  prescriptions: Prescription[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private prescriptionService: PrescriptionService,
    private location:Location
  ) {}

  ngOnInit(): void {
    this.appointmentId = Number(this.route.snapshot.paramMap.get('appointmentId'));
    this.loadPrescriptions();
  }

  loadPrescriptions(): void {
    this.prescriptionService.getPrescriptionsByAppointmentId(this.appointmentId)
      .subscribe({
        next: (data) => {
          this.prescriptions = data;
        },
        error: (err) => {
          console.error('Error loading prescriptions', err);
        }
      });
  }

  deletePrescription(prescriptionId: number): void {
    const confirmed = confirm('Are you sure you want to delete this prescription?');
    if (confirmed) {
      this.prescriptionService.deletePrescription(prescriptionId)
        .subscribe({
          next: () => {
            // Reload after deletion
            this.prescriptions = this.prescriptions.filter(p => p.prescriptionId !== prescriptionId);
          },
          error: (err) => {
            console.error('Error deleting prescription', err);
          }
        });
    }
  }

  goBack()
  {
    this.location.back();
  }

}


import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Prescription } from 'src/app/model/Prescription';
import { PrescriptionService } from 'src/app/service/prescription.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-view-prescription',
  templateUrl: './view-prescription.component.html',
  styleUrls: ['./view-prescription.component.css']
})
export class ViewPrescriptionComponent implements OnInit {

  appointmentId!: number;
  prescriptions: Prescription[] = [];

  constructor(
    private route: ActivatedRoute,
    private prescriptionService: PrescriptionService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.appointmentId = Number(this.route.snapshot.paramMap.get('appointmentId'));
    this.fetchPrescriptionsByAppointment();
  }

  fetchPrescriptionsByAppointment(): void {
    this.prescriptionService.getPrescriptionsByAppointmentId(this.appointmentId).subscribe({
      next: (data) => {
        console.log(data);
        this.prescriptions = data;
        if (data.length === 0) {
          alert('No prescriptions found.');
          this.location.back();
        }
      },
      error: (err) => {
        console.error('Error fetching prescriptions:', err);
        alert('Error loading prescriptions.');
        this.location.back();
      }
    });
  }

  goBack(): void {
    this.location.back();
  }
}

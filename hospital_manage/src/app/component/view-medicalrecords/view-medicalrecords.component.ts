
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MedicalRecord } from 'src/app/model/MedicalRecord';
import { MedicalrecordsService } from 'src/app/service/medicalrecords.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-view-medicalrecords',
  templateUrl: './view-medicalrecords.component.html',
  styleUrls: ['./view-medicalrecords.component.css']
})
export class ViewMedicalrecordsComponent implements OnInit {

  appointmentId!: number;
  medicalRecord!: MedicalRecord;

  constructor(
    private route: ActivatedRoute,
    private medicalService: MedicalrecordsService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.appointmentId = Number(this.route.snapshot.paramMap.get('appointmentId'));
    this.medicalService.getMedicalRecordByAppointmentId(this.appointmentId).subscribe({
      next: (record) => {
        this.medicalRecord = record;
        console.log("Fetched Medical Record:", record);
      },
      error: (err) => {
        console.error('Error fetching medical record:', err);
        alert('Medical record not found.');
        this.location.back();
      }
    });
  }

  goBack(): void {
    this.location.back();
  }
}


import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MedicalRecord } from 'src/app/model/MedicalRecord';
import { MedicalrecordsService } from 'src/app/service/medicalrecords.service';
import { Location } from '@angular/common';
@Component({
  selector: 'app-view-all-medicalrecords',
  templateUrl: './view-all-medicalrecords.component.html',
  styleUrls: ['./view-all-medicalrecords.component.css']
})
export class ViewAllMedicalrecordsComponent implements OnInit {

  patientId!: number;
  medicalRecord!: MedicalRecord[];

  constructor(
    private route: ActivatedRoute,
    private medicalService: MedicalrecordsService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.patientId = Number(this.route.snapshot.paramMap.get('patientId'));
    this.medicalService.getMedicalRecordsByPatientId(this.patientId).subscribe({
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

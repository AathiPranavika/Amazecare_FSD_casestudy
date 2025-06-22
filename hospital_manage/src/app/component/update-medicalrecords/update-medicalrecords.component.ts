import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicalrecordsService } from 'src/app/service/medicalrecords.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-update-medicalrecords',
  templateUrl: './update-medicalrecords.component.html',
  styleUrls: ['./update-medicalrecords.component.css']
})
export class UpdateMedicalrecordsComponent implements OnInit {

  medicalRecord = {
    recordId: 0,
    appointmentId: 0,
    symptoms: '',
    physicalExam: '',
    diagnosis: '',
    treatmentPlan: ''
  };
  appointmentId:number=0;
  constructor(
    private route: ActivatedRoute,
    private medicalService: MedicalrecordsService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    const appId = Number(this.route.snapshot.paramMap.get('appointmentId'));
    if (appId) {
      this.appointmentId=appId;
      this.medicalService.getMedicalRecordByAppointmentId(appId).subscribe({
        next: (record) => {
       console.log("Fetched record:", record);
      this.medicalRecord = {
       ...record,
     appointmentId: this.appointmentId  
      };
},       error: () => {
          alert('No medical record found for this appointment.');
          this.router.navigate(['/doctor-dashboard']);
        }
      });
    }
  }

  onSubmit(): void {
        this.medicalRecord.appointmentId = this.appointmentId;
    this.medicalService.updateMedicalRecord(this.medicalRecord.recordId, this.medicalRecord).subscribe({
      next: () => {
        alert("Medical record updated successfully!");
        this.location.back();
      },
      error: (err) => {
        console.error("Update failed:", err);
        alert("Failed to update medical record.");
      }
    });
  }

  goBack(): void {
    this.location.back();
  }
}

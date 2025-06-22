import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { MedicalrecordsService } from 'src/app/service/medicalrecords.service';
import {Location} from '@angular/common';

@Component({
  selector: 'app-add-medicalrecords',
  templateUrl: './add-medicalrecords.component.html',
  styleUrls: ['./add-medicalrecords.component.css']
})
export class AddMedicalrecordsComponent implements OnInit {

  appId: number = 0;

  constructor(
    private route: ActivatedRoute,
    private medicalService: MedicalrecordsService,
    private authService: AuthenticationService,
    private location:Location
  ) {

  }

  ngOnInit(): void {
  this.appId = Number(this.route.snapshot.paramMap.get('appointmentId'));
  this.checkIfRecordExists(this.appId);
}
  checkIfRecordExists(appointmentId: number): void {
    this.medicalService.getMedicalRecordByAppointmentId(appointmentId).subscribe({
      next: (record) => {
        if (record) {
          alert("A medical record already exists for this appointment.");
          this.location.back();
        }
      },
      error: () => {
        console.log("no prior records exit..u can add records")
      }
    });
  }

  createMedicalRecord(formData: any): void {
    formData.appointmentId = this.appId; 
    this.medicalService.createMedicalRecord(formData).subscribe({
      next: () =>
        { alert("Medical record created successfully!"),
          this.location.back();
        },
      error: (err) => console.error("Error saving record:", err)
    });
  }

  goBack()
  {
    this.location.back();
  }
}

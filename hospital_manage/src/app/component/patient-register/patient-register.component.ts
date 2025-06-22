import { Component, OnInit } from '@angular/core';
import { Patient } from 'src/app/model/Patient';
import { PatientService } from 'src/app/service/patient.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import {Location} from '@angular/common';
@Component({
  selector: 'app-patient-register',
  templateUrl: './patient-register.component.html',
  styleUrls: ['./patient-register.component.css']
})
export class PatientRegisterComponent implements OnInit {
  bloodGroups = ['A+', 'A-', 'B+', 'B-', 'O+', 'O-', 'AB+', 'AB-'];
  userId: number = 0;
  alreadyRegistered = false;

  constructor(
    private patientService: PatientService,
    private authService: AuthenticationService,
    private location:Location
  ) {}

  ngOnInit(): void {
      //getting current loggedin user id
      const userId = this.authService.getUserId();
      if (userId !== null) {
        this.userId = userId;
      }  
      //checking if already registered as patient
      this.patientService.checkPatientExistsByUserId(this.userId).subscribe({
      next: (patient) => {
        if (patient) {
          this.alreadyRegistered = true;
        }
      },
      error: (err) => {
        console.log("Patient not found, new registration allowed.");
      }
    });
  }

  registerPatient(data: Patient) {
    if (this.alreadyRegistered) {
      alert("Patient profile already exists.");
      return;
    }
    //adding userId to patientDetails
    const patientDetails: Patient = {
      ...data,
      userId: this.userId
    };

    this.patientService.addPatientDetails(patientDetails).subscribe({
      next: () => {
        alert("Patient profile created successfully!");
      },
      error: (err) => {
        console.error("Error while creating patient:", err);
      }
    });
  }

  goBack()
  {
    this.location.back();
  }
}

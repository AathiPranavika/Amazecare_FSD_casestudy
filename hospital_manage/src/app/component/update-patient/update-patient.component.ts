import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Patient } from 'src/app/model/Patient';
import { PatientService } from 'src/app/service/patient.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-update-patient',
  templateUrl: './update-patient.component.html',
  styleUrls: ['./update-patient.component.css']
})
export class UpdatePatientComponent implements OnInit {

patient: Patient = {
  patientId: 0,
  userId: 0,
  user: {
    userId: 0,
    name: '',
    email: '',
    password: '',
    role: 'PATIENT',
    gender: 'Other',
    dateOfBirth: '',
    contactNumber: '',
    createdAt: '',
    doctor: undefined,
    patient: undefined,
    admin: undefined
  },
  address: '',
  emergencyContact: '',
  bloodGroup: 'A+',
  medicalHistory: '',
  appointments: []
};

  bloodGroups: string[] = ['A+', 'A-', 'B+', 'B-', 'O+', 'O-', 'AB+', 'AB-'];

  constructor(
    private patientService: PatientService,
    private location: Location,
    private router: Router,
    private route: ActivatedRoute 
  ) {}

  ngOnInit(): void {
    const userId = Number(this.route.snapshot.paramMap.get('id'));
    console.log("User ID from route:", userId);

    if (userId) {
      this.patientService.getPatientDetailsByUserId(userId).subscribe({
        next: (data) => {
          console.log("Fetched patient details:", data);
          this.patient = data;
        },
        error: () => {
          alert('Patient details not found for the given ID');
          this.router.navigate(['/patient-dashboard']);
        }
      });
    } else {
      alert('Invalid user ID');
      this.router.navigate(['/login']);
    }
  }

  onSubmit(): void {
    this.patientService.updatePatient(this.patient.patientId, this.patient).subscribe({
      next: () => {
        alert('Patient details updated successfully!');
        this.location.back();
      },
      error: (err) => {
        console.error('Update error:', err);
        alert('Error updating patient. Please try again.');
      }
    });
  }

  goBack(): void {
    this.location.back();
  }
}

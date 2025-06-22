import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Doctor } from 'src/app/model/Doctor';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { PatientService } from 'src/app/service/patient.service';

@Component({
  selector: 'app-patient-dashboard',
  templateUrl: './patient-dashboard.component.html',
  styleUrls: ['./patient-dashboard.component.css']
})
export class PatientDashboardComponent implements OnInit {

  userId: number | null = null;
  username:string='';
  patientId:number=0;
  constructor(
    private authService: AuthenticationService,
    private patientService:PatientService,
    private router:Router
  ) {}

  ngOnInit(): void {
    this.userId = this.authService.getUserId();
    this.username=this.authService.getUsername();
    this.patientService.getPatientDetailsByUserId(this.userId).subscribe({
        next: (data) => {
          console.log("Fetched patient details:", data);
          this.patientId = data.patientId;
        },
        error: () => {
          alert('Patient details not found for the given ID');
          this.router.navigate(['/patient-dashboard']);
        }
      });
    }
    }


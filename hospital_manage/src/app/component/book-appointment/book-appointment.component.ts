import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PatientService } from 'src/app/service/patient.service';
import { DoctorService } from 'src/app/service/doctor.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { AppointmentDTO } from 'src/app/model/AppointmentDTO';  
import { Location } from '@angular/common';
@Component({
  selector: 'app-book-appointment',
  templateUrl: './book-appointment.component.html',
  styleUrls: ['./book-appointment.component.css']
})
export class BookAppointmentComponent implements OnInit {

  doctors: any[] = [];
  patientId: number = 0;
  loggedInUserId: number = 0;

  appointment: AppointmentDTO = {
    patientId: 0,
    doctorId: 0,
    appointmentDate: '',
    symptoms: '',
    status: 'PENDING',
    appointmentId: 0
  };

  constructor(
    private patientService: PatientService,
    private doctorService: DoctorService,
    private router: Router,
    private authenticationService: AuthenticationService,
    private location:Location
  ) {}

  ngOnInit(): void {
    this.loadAllDoctors();
    this.loadPatientId();
  }

  loadPatientId(): void {
    const userId = this.authenticationService.getUserId();
    if (userId !== null) {
      this.loggedInUserId = userId;
    }

    this.patientService.getPatientDetailsByUserId(this.loggedInUserId).subscribe({
      next: (patientData) => {
        this.patientId = patientData.patientId;
        this.appointment.patientId = this.patientId; 
      },
      error: (err) => {
        console.error('Error fetching patient by user ID:', err);
      }
    });
  }

  loadAllDoctors(): void {
    this.doctorService.getAllDoctors().subscribe({
      next: (data) => {
        this.doctors = data;
      },
      error: (err) => {
        console.error('Error fetching doctors:', err);
      }
    });
  }

  bookAppointment(): void {
    this.appointment.status = 'PENDING'; 
    console.log('Booking appointment:', this.appointment);

    this.patientService.bookAppointment(this.appointment).subscribe({
      next: () => {
        alert('Appointment booked successfully!');
        this.router.navigate(['/patient-dashboard']);
      },
      error: (err) => {
        console.error('Booking failed:', err);
        alert('Appointment booking failed.');
      }
    });
  }

   goBack()
  {
    this.location.back();
  }
}

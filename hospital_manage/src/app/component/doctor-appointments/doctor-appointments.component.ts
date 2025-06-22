
import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Appointment } from 'src/app/model/Appointment';
import { AppointmentService } from 'src/app/service/appointment.service';
import { DoctorService } from 'src/app/service/doctor.service';
import { PatientService } from 'src/app/service/patient.service';
import {Location} from '@angular/common';
@Component({
  selector: 'app-doctor-appointments',
  templateUrl: './doctor-appointments.component.html',
  styleUrls: ['./doctor-appointments.component.css']
})
export class DoctorAppointmentsComponent implements OnInit {

  appointments: Appointment[] = [];
  filteredAppointments: Appointment[] = [];

  patientIdFilter: string = '';
  statusFilter: string = '';

  statuses: string[] = ['PENDING', 'CONFIRMED', 'COMPLETED', 'CANCELLED', 'REJECTED'];

  userId: number = 0;
  doctorId: number = 0;

  constructor(
    private doctorService: DoctorService,
    private route: ActivatedRoute,
    private router: Router,
    private appointmentService: AppointmentService,
    private location:Location
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get("id"));
    if (!isNaN(id)) {
      this.userId = id;
      this.doctorService.getDoctorByUserId(this.userId).subscribe({
        next: (doctor) => {
          this.doctorId = doctor.doctorId;
          this.loadAllAppointments();
        },
        error: (err) => {
          console.error("Error fetching patient details", err);
        }
      });
    }
  }

  loadAllAppointments(): void {
    this.appointmentService.getAppointmentsByDoctorId(this.doctorId).subscribe({
      next: (data) => {
        this.appointments = data;
        this.filteredAppointments = data;
      },
      error: (err) => console.error('Error fetching appointments:', err)
    });
  }

  filterByPatientId(): void {
    const patientId = this.patientIdFilter;
    if (patientId === '') {
      this.loadAllAppointments();
    } else {
      this.appointmentService.getAppointmentsByPatientAndDoctor(Number(patientId),this.doctorId).subscribe({
        next: (data) => this.filteredAppointments = data,
        error: (err) => console.error('Error filtering by doctor ID', err)
      });
    }
  }

   filterByStatus(): void {
  if (this.statusFilter === '') {
    this.loadAllAppointments();
  } else {
    this.appointmentService.getAppointmentsByDoctorAndStatus(this.doctorId, this.statusFilter).subscribe({
      next: (data) => this.filteredAppointments = data,
      error: (err) => console.error('Error filtering by patient and status', err)
    });
  }
}
   
  clearFilters(): void {
    this.patientIdFilter = '';
    this.statusFilter = '';
    this.filteredAppointments = this.appointments;
  }

  

  goBack(): void {
    this.location.back();
  }
  
}

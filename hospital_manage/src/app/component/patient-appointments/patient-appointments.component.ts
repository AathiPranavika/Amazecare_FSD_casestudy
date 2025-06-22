import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Appointment } from 'src/app/model/Appointment';
import { AppointmentService } from 'src/app/service/appointment.service';
import { PatientService } from 'src/app/service/patient.service';
import {Location} from '@angular/common';
@Component({
  selector: 'app-patient-appointments',
  templateUrl: './patient-appointments.component.html',
  styleUrls: ['./patient-appointments.component.css']
})
export class PatientAppointmentsComponent implements OnInit {

  appointments: Appointment[] = [];
  filteredAppointments: Appointment[] = [];

  doctorIdFilter: string = '';
  statusFilter: string = '';
  dateFilter: string = '';

  statuses: string[] = ['PENDING', 'CONFIRMED', 'COMPLETED', 'CANCELLED', 'REJECTED'];

  userId: number = 0;
  patientId: number = 0;

  constructor(
    private patientService: PatientService,
    private route: ActivatedRoute,
    private router: Router,
    private appointmentService: AppointmentService,
    private location:Location
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get("id"));
    if (!isNaN(id)) {
      this.userId = id;
      this.patientService.getPatientDetailsByUserId(this.userId).subscribe({
        next: (patient) => {
          this.patientId = patient.patientId;
          this.loadAllAppointments();
        },
        error: (err) => {
          console.error("Error fetching patient details", err);
        }
      });
    }
  }

  loadAllAppointments(): void {
    this.appointmentService.getAppointmentsByPatientId(this.patientId).subscribe({
      next: (data) => {
        this.appointments = data;
        this.filteredAppointments = data;
      },
      error: (err) => console.error('Error fetching appointments:', err)
    });
  }

  filterByDoctorId(): void {
    const doctorId = this.doctorIdFilter;
    if (doctorId === '') {
      this.loadAllAppointments();
    } else {
      this.appointmentService.getAppointmentsByPatientAndDoctor(this.patientId,Number(doctorId)).subscribe({
        next: (data) => this.filteredAppointments = data,
        error: (err) => console.error('Error filtering by doctor ID', err)
      });
    }
  }

  // Filter by Appointment Status
  filterByStatus(): void {
  if (this.statusFilter === '') {
    this.loadAllAppointments();
  } else {
    this.appointmentService.getAppointmentsByPatientAndStatus(this.patientId, this.statusFilter).subscribe({
      next: (data) => this.filteredAppointments = data,
      error: (err) => console.error('Error filtering by patient and status', err)
    });
  }
}

   
  clearFilters(): void {
    this.doctorIdFilter = '';
    this.statusFilter = '';
    this.dateFilter = '';
    this.filteredAppointments = this.appointments;
  }

  cancelAppointment(appointmentId: number): void {
  this.appointmentService.getAppointmentById(appointmentId).subscribe({
    next: (appointment) => {
      if (appointment.status === 'CANCELLED') {
        alert('Appointment is already cancelled.');
      } else {
        if (confirm('Are you sure you want to cancel this appointment?')) {
          this.appointmentService.cancelAppointment(appointmentId).subscribe({
            next: () => {
              alert('Appointment cancelled successfully.');
              this.loadAllAppointments();
            },
            error: (err) => {
              console.error('Failed to cancel appointment:', err);
              alert('Cancellation failed.');
            }
          });
        }
      }
    },
    error: (err) => {
      console.error('Error fetching appointment details:', err);
      alert('Failed to fetch appointment details.');
    }
  });
}


  
  goBack(): void {
    this.location.back();
  }
  
}

import { Component, OnInit } from '@angular/core';
import { AppointmentService } from 'src/app/service/appointment.service';
import { Appointment } from 'src/app/model/Appointment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-appointment',
  templateUrl: './view-appointment.component.html',
  styleUrls: ['./view-appointment.component.css']
})
export class ViewAppointmentComponent implements OnInit {

  appointments: Appointment[] = [];
  filteredAppointments: Appointment[] = [];

  patientIdFilter: string = '';
  doctorIdFilter: string = '';
  statusFilter: string = '';

  statuses: string[] = ['PENDING', 'CONFIRMED', 'COMPLETED', 'CANCELLED', 'REJECTED'];

  constructor(
    private appointmentService: AppointmentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadAllAppointments();
  }

  // Load all appointments
  loadAllAppointments(): void {
    this.appointmentService.getAllAppointments().subscribe({
      next: (data) => {
        this.appointments = data;
        this.filteredAppointments = data;
      },
      error: (err) => {
        console.error('Error fetching appointments:', err);
      }
    });
  }

  // Filter by Patient ID
  filterByPatientId(): void {
    const id = this.patientIdFilter;
    if (id === '') {
      this.loadAllAppointments();
    } else {
      this.appointmentService.getAppointmentsByPatientId(Number(id)).subscribe({
        next: (data) => this.filteredAppointments = data,
        error: (err) => console.error('Error filtering by patient ID', err)
      });
    }
  }

  // Filter by Doctor ID
  filterByDoctorId(): void {
    const id = this.doctorIdFilter;
    if (id === '') {
      this.loadAllAppointments();
    } else {
      this.appointmentService.getAppointmentsByDoctorId(Number(id)).subscribe({
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
      this.appointmentService.getAppointmentsByStatus(this.statusFilter).subscribe({
        next: (data) => this.filteredAppointments = data,
        error: (err) => console.error('Error filtering by status', err)
      });
    }
  }



  // Clear all filters
  clearFilters(): void {
    this.patientIdFilter = '';
    this.doctorIdFilter = '';
    this.statusFilter = '';
    this.loadAllAppointments();
  }

  // Delete Appointment
  deleteAppointment(appointmentId: number): void {
    if (confirm('Are you sure you want to delete this appointment?')) {
      this.appointmentService.deleteAppointment(appointmentId).subscribe({
        next: () => {
          alert('Appointment deleted successfully.');
          this.loadAllAppointments();
        },
        error: (err) => {
          console.error('Delete failed:', err);
          alert('Failed to delete appointment.');
        }
      });
    }
  }

  // Navigate back
  goBack(): void {
    this.router.navigate(['/admin-dashboard']);
  }
}

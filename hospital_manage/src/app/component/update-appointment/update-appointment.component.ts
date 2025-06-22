
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppointmentService } from 'src/app/service/appointment.service';
import { Appointment } from 'src/app/model/Appointment';
import { DoctorService } from 'src/app/service/doctor.service';
import { Doctor } from 'src/app/model/Doctor';
import { Location } from '@angular/common';
import { AppointmentDTO } from 'src/app/model/AppointmentDTO';

@Component({
  selector: 'app-update-appointment',
  templateUrl: './update-appointment.component.html',
  styleUrls: ['./update-appointment.component.css']
})
export class UpdateAppointmentComponent implements OnInit {

  appointmentId: number = 0;

  appointment!:Appointment;
minDateTime: string = '';

  doctors: Doctor[] = [];

  constructor(
    private route: ActivatedRoute,
    private appointmentService: AppointmentService,
    private doctorService: DoctorService,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    //to disable past dates in form
     const now = new Date();
     this.minDateTime = now.toISOString().slice(0, 16);

    this.appointmentId = Number(this.route.snapshot.paramMap.get('id'));

    if (!this.appointmentId) {
      alert('Invalid appointment ID!');
      this.router.navigate(['/appointment-list']);
      return;
    }

    // Get appointment by ID
    this.appointmentService.getAppointmentById(this.appointmentId).subscribe({
      next: (data) => {
        this.appointment = data;
      },
      error: () => {
        alert('Appointment not found.');
        this.router.navigate(['/appointment-list']);
      }
    });

    // Load doctor list
    this.doctorService.getAllDoctors().subscribe({
      next: (data) => {
        this.doctors = data;
      },
      error: () => {
        alert('Failed to load doctor list');
      }
    });
  }

  updateAppointment() {
    if (!this.appointment || !this.appointment.appointmentId) {
      alert('Appointment ID missing. Cannot update.');
      return;
    }

    const dto:AppointmentDTO= {
  appointmentId: this.appointment.appointmentId,
  appointmentDate: this.appointment.appointmentDate,
  symptoms: this.appointment.symptoms,
  status: this.appointment.status,
  doctorId: this.appointment.doctor.doctorId,
  patientId: this.appointment.patient.patientId
};

this.appointmentService.updateAppointment(this.appointment.appointmentId, dto).subscribe({
      next: () => {
        alert('Appointment updated successfully!');
        this.location.back();
      },
      error: (err) => {
        console.error('Update error:', err);
        alert('Error updating appointment. Please try again.');
      }
    });
  }

  goBack() {
    this.location.back();
  }
}

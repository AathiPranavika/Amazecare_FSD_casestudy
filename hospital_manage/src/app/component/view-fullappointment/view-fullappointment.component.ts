import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Appointment } from 'src/app/model/Appointment';
import { AppointmentService } from 'src/app/service/appointment.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-view-fullappointment',
  templateUrl: './view-fullappointment.component.html',
  styleUrls: ['./view-fullappointment.component.css']
})
export class ViewFullappointmentComponent implements OnInit {

  appointmentId!: number;
  appointment!: Appointment;

  constructor(
    private route: ActivatedRoute,
    private appointmentService: AppointmentService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.appointmentId = Number(this.route.snapshot.paramMap.get('id'));

    this.appointmentService.getAppointmentById(this.appointmentId).subscribe({
      next: (data) => {
        this.appointment = data;
        console.log('Appointment data:', data);
      },
      error: (err) => {
        console.error('Error loading appointment:', err);
        alert('Failed to load appointment.');
      }
    });
  }

  goBack(): void {
    this.location.back();
  }
}

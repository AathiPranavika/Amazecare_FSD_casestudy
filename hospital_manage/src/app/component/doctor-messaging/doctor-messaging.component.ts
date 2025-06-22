import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DoctorService } from 'src/app/service/doctor.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-doctor-messaging',
  templateUrl: './doctor-messaging.component.html',
  styleUrls: ['./doctor-messaging.component.css']
})
export class DoctorMessagingComponent implements OnInit {

  doctorId!: number;
  patientId!: number;
  userId!: number;
  messages: any[] = [];

  messageDTO: any = {
    doctorId: 0,
    patientId: 0,
    message: '',
    senderRole: 'DOCTOR'
  };

  constructor(
    private route: ActivatedRoute,
    private doctorService: DoctorService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.patientId = Number(this.route.snapshot.paramMap.get('patientId'));
    this.userId = Number(localStorage.getItem('userId'));

    this.doctorService.getDoctorByUserId(this.userId).subscribe(doctor => {
      this.doctorId = doctor.doctorId;
      this.messageDTO.doctorId = this.doctorId;
      this.messageDTO.patientId = this.patientId;

      console.log("doctorId: " + this.doctorId);
      console.log("patientId: " + this.patientId);

      this.getAllMessages();
    });
  }

  getAllMessages(): void {
    this.doctorService.getMessagesBetweenDoctorAndPatient(this.doctorId, this.patientId).subscribe({
      next: (data) => {
        this.messages = data;
        console.log('Messages fetched:', data);
      },
      error: (err) => {
        console.error('Error fetching messages:', err);
      }
    });
  }

  sendMessage(): void {
    if (this.messageDTO.message.trim() === '') return;

    this.doctorService.sendMessageAsDoctor(this.messageDTO).subscribe({
      next: () => {
        this.messageDTO.message = '';
        this.getAllMessages();
      },
      error: (err) => {
        console.error("Send message failed:", err);
        alert("Message sending failed.");
      }
    });
  }

  goBack() {
    this.location.back();
  }
}

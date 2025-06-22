import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PatientService } from 'src/app/service/patient.service';
import {Location} from '@angular/common';
@Component({
  selector: 'app-patient-messaging',
  templateUrl: './patient-messaging.component.html',
  styleUrls: ['./patient-messaging.component.css']
})
export class PatientMessagingComponent implements OnInit {

  doctorId!: number;
  patientId!: number;
  userId!: number;
  messages: any[] = [];

  messageDTO: any = {
    doctorId: 0,
    patientId: 0,
    message: '',
    senderRole:'PATIENT' 

  };  
  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService,
    private location:Location
  ) {}

  ngOnInit(): void {
    this.doctorId = Number(this.route.snapshot.paramMap.get('doctorId'));
    this.userId = Number(localStorage.getItem('userId'));

    // Fetch patientId using userId
    this.patientService.getPatientDetailsByUserId(this.userId).subscribe(patient => {
      this.patientId = patient.patientId;
      this.messageDTO.patientId = this.patientId;
      this.messageDTO.doctorId = this.doctorId;
      console.log("patientId"+this.patientId)
      console.log("doctorId"+this.doctorId)
      this.getAllMessages();
    });
  }

  getAllMessages(): void {
  this.patientService.getMessagesBetweenDoctorAndPatient(this.doctorId, this.patientId)
    .subscribe({
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

  this.patientService.sendMessageAsPatient(this.messageDTO).subscribe({
    next: () => {
      this.messageDTO.message = '';
      this.getAllMessages(); 
    },
    error: (err) => {
      console.error("Send message failed:", err);
      alert(" Message sending failed: ");
    }
  });
}

goBack()
{
  this.location.back();
}
}

import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Doctor } from 'src/app/model/Doctor';
import { DoctorService } from 'src/app/service/doctor.service';
import {Location} from '@angular/common'
@Component({
  selector: 'app-update-doctor',
  templateUrl: './update-doctor.component.html',
  styleUrls: ['./update-doctor.component.css']
})
export class UpdateDoctorComponent implements OnInit{

  userId:number=0;
  doctor: Doctor ={
    doctorId: 0,
    userId: 0,
    specialization: '',
    experienceYears: 0,
    qualification: '',
    designation: '',
    user: {
      userId: 0,
      name: '',
      email: '',
      password: '',
      role: 'DOCTOR',
      gender: 'Other',
      dateOfBirth: '',
      contactNumber: '',
      createdAt: '',
    }
}
  constructor(private router:ActivatedRoute,
    private doctorService:DoctorService,
    private route:Router,
    private location:Location)
  {

  }

  ngOnInit():void{
    const userId=Number(this.router.snapshot.paramMap.get("id"));
    this.userId=userId

    this.doctorService.getDoctorByUserId(this.userId).subscribe(
      {
        next:(data)=>
        {
          this.doctor=data;
        },
        error:(err)=>
        {
           alert('Doctor details not found for the given ID');
          this.route.navigate(['/doctor-dashboard']);
        }
      }
    )
  }

  updateDoctor()
  {
    if (!this.doctor || !this.doctor.doctorId) {
    alert("Doctor ID is missing. Cannot update.");
    return;
  }
    this.doctorService.updateDoctor(this.doctor.doctorId, this.doctor).subscribe({
      next: () => {
        alert('Doctor details updated successfully!');
        this.location.back();
      },
      error: (err) => {
        console.error('Update error:', err);
        alert('Error updating dotor. Please try again.');
      }
    });
  }

  goBack()
  {
    this.location.back();
  }
}

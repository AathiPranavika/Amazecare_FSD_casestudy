import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Doctor } from 'src/app/model/Doctor';
import { DoctorService } from 'src/app/service/doctor.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-doctor-register',
  templateUrl: './doctor-register.component.html',
  styleUrls: ['./doctor-register.component.css']
})
export class DoctorRegisterComponent implements OnInit {

  userId:number=0;
  alreadyRegistered = false;

  constructor(private doctorService:DoctorService,private router:ActivatedRoute,private location:Location)
  {

  }

ngOnInit():void{
  const userId=Number(this.router.snapshot.paramMap.get("id"));
   if (userId !== null)
     {
        this.userId = userId;
      } 
   this.doctorService.checkDoctorExistsByUserId(userId).subscribe(
    {
      next:(doctor)=>
      {
        if (doctor){
         this.alreadyRegistered=true;
        }
      },
       error: (err) => {
        console.log("Doctor not found, new registration allowed.");
      }
    }
   )    
}
  registerDoctor(data:Doctor)
  {
    if(this.alreadyRegistered)
    {
      alert("Doctor profile already exists.");
      return;
    }
     const doctorDetails: Doctor = {
          ...data,
          userId: this.userId
        };
      this.doctorService.addDoctorDetails(doctorDetails).subscribe({
       next: () => {
        alert("Doctor profile created successfully!");
      },
      error: (err) => {
        console.error("Error while creating doctor:", err);
      }
    });
  }

  goBack()
  {
    this.location.back();
  }
  
}

import { OnInit } from '@angular/core';
import { Component } from '@angular/core';

import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-doctor-dashboard',
  templateUrl: './doctor-dashboard.component.html',
  styleUrls: ['./doctor-dashboard.component.css']
})
export class DoctorDashboardComponent implements OnInit{

  
    userId:Number=0;
    username:string='';
  constructor(private authService:AuthenticationService)
  {

  }

  ngOnInit():void
  {
      this.userId=this.authService.getUserId();
      this.username=this.authService.getUsername();
  }

 
}
 
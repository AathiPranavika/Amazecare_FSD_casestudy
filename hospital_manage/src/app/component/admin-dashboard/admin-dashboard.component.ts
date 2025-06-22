import { Component } from '@angular/core';
import { User } from 'src/app/model/User';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-admin-dash-board',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashBoardComponent {

  
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

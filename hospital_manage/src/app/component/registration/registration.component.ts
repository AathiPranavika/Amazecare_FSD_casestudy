import { Component } from '@angular/core';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/service/user.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
title = 'user_registration';

constructor(private userService:UserService,private location:Location)
{

}
  userDetails!:User;

  allUserDetails:User[]=[];

  fetchUserData(data:User)
  {
        this.userDetails=data;
       this.userService.addUser(this.userDetails).subscribe({
  next: (response) => {
    alert("Registration successful");
     this.location.back();
  },
  error: (err) => {
    console.error("Error during registration:", err);
    alert(err.message);
  }
});

  }

  goBack()
  {
    this.location.back();
  }

  

}

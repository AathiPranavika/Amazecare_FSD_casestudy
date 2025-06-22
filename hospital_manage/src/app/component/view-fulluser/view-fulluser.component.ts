import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/service/user.service';
import {Location} from '@angular/common';
@Component({
  selector: 'app-view-fulluser',
  templateUrl: './view-fulluser.component.html',
  styleUrls: ['./view-fulluser.component.css']
})
export class ViewFulluserComponent implements OnInit {

    userId!: number;
    user!: User;

  constructor(private route: ActivatedRoute, private userService: UserService,private loaction:Location) {}

  ngOnInit(): void {
    this.userId = Number(this.route.snapshot.paramMap.get('id'));
    this.userService.getUserById(this.userId).subscribe({
      next: (data) => {
        this.user = data;
        console.log("Full User Data:", data);
      },
      error: (err) => {
        console.error('Error fetching user:', err);
      }
    });
  }

  goBack():void
{
  this.loaction.back();
}
}

import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/service/user.service';
import { Location } from '@angular/common';
@Component({
  selector: 'app-display-allusers',
  templateUrl: './display-allusers.component.html',
  styleUrls: ['./display-allusers.component.css']
})
export class DisplayAllusersComponent implements OnInit {

  allUserDetails: User[] = [];

  constructor(private userService: UserService,private location:Location) {}

  ngOnInit(): void {
    this.getAllUsers();
  }

  getAllUsers(): void {
    this.userService.getAll().subscribe(
      (userDetails) => {
        console.log('All users:', userDetails);
        this.allUserDetails = userDetails;
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );
  }

  deleteUser(id: number): void {
    this.userService.deleteUserById(id).subscribe(
      () => {
        console.log('User deleted with id', id);
        alert('Deleted user with Id ' + id);
        this.getAllUsers(); // Refresh list after delete
      },
      (error) => {
        console.error('Error deleting user:', error);
      }
    );
  }

  goBack()
  {
    this.location.back();
  }
}


import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/User';
import { UserService } from 'src/app/service/user.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent  implements OnInit {
  user!: User;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location,
    public router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.userService.getUserById(id).subscribe({
      next: (data) => {
        this.user = data;
        console.log(data);
      },
      error: () => {
        alert('User not found');
        this.router.navigate(['/admin-dashboard']);
      }
    });
  }

  onSubmit(): void {
    this.userService.updateUser(this.user.userId, this.user).subscribe({
      next: () => {
        alert('User updated successfully with Id '+this.user.userId);
       this.location.back();
      },
      error: (err) => {
        console.error('Update error:', err);
        alert('Error updating user. Please try again.');
      }
    });
  }


  goBack(): void {
    this.location.back(); 
  }

}

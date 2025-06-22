import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';
import {Location} from '@angular/common';
@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  constructor(private userService: UserService, private router: Router,private location:Location) {}

  resetPassword(formData: any): void {
    this.userService.resetPassword(formData.email,formData.newPassword).subscribe({
      next: () => {
        alert('Password updated successfully!');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Password reset failed', err);
      if (err.message.includes('User not found')) {
        alert(' User not found. Please check the email and try again.');
      } 
      else {
        alert('Failed to update password. Please try again later.');
      }
    }      
    });
  }

  
  goBack() {
    this.location.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { Admin } from 'src/app/model/Admin';
import { AdminService } from 'src/app/service/admin.service';
import { AuthenticationService } from 'src/app/service/authentication.service';
import {Location} from '@angular/common';

@Component({
  selector: 'app-admin-register',
  templateUrl: './admin-register.component.html',
  styleUrls: ['./admin-register.component.css']
})
export class AdminRegisterComponent implements OnInit {

  userId: number = 0;
  alreadyRegistered = false;

  constructor(
    private adminService: AdminService,
    private authService: AuthenticationService,
    private location:Location
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (userId !== null) {
      this.userId = userId;
    }

    this.adminService.getAdminByUserId(this.userId).subscribe({
      next: (admin) => {
        if (admin) {
          this.alreadyRegistered = true;
        }
      },
      error: () => {
        console.log("Admin not found, new registration allowed.");
      }
    });
  }

  registerAdmin(data: Admin): void {
    if (this.alreadyRegistered) {
      alert("Admin profile already exists.");
      return;
    }

    const adminDetails: Admin = {
      ...data,
      userId: this.userId
    };

    this.adminService.addAdminDetails(adminDetails).subscribe({
      next: () => {
        alert("Admin profile created successfully!");
      },
      error: (err) => {
        console.error("Error creating admin:", err);
      }
    });
  }

  goBack()
  {
    this.location.back();
  }
}

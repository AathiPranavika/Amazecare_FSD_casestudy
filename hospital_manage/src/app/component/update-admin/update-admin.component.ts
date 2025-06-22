import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Admin } from 'src/app/model/Admin';
import { AdminService } from 'src/app/service/admin.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-update-admin',
  templateUrl: './update-admin.component.html',
  styleUrls: ['./update-admin.component.css']
})
export class UpdateAdminComponent implements OnInit {

  admin: Admin = {
    adminId: 0,
    userId: 0,
    adminCode: '',
    department: '',
    qualification: '',
    user: {
      userId: 0,
      name: '',
      email: '',
      password: '',
      role: 'ADMIN',
      gender: 'Other',
      dateOfBirth: '',
      contactNumber: '',
      createdAt: '',
      doctor: undefined,
      patient: undefined,
      admin: undefined
    }
  };

  constructor(
    private adminService: AdminService,
    private location: Location,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const userId = Number(this.route.snapshot.paramMap.get('id'));
    console.log("User ID from route:", userId);

    if (userId) {
      this.adminService.getAdminByUserId(userId).subscribe({
        next: (data) => {
          console.log("Fetched admin details:", data);
          this.admin = data;
        },
        error: () => {
          alert('Admin details not found for the given ID');
          this.router.navigate(['/admin-dashboard']);
        }
      });
    } else {
      alert('Invalid user ID');
      this.router.navigate(['/login']);
    }
  }

  onSubmit(): void {
    this.adminService.updateAdmin(this.admin.adminId, this.admin).subscribe({
      next: () => {
        alert('Admin details updated successfully!');
        this.location.back();
      },
      error: (err) => {
        console.error('Update error:', err);
        if(err.message === 'Admin code already exists')
        {
        alert(err.message);
        }
        else{
        alert('Error updating admin. Please try again.');
        }
      }
    });
  }

  goBack(): void {
    this.location.back();
  }
}

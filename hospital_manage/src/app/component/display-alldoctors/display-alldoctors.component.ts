import { Component, OnInit } from '@angular/core';
import { Doctor } from 'src/app/model/Doctor';
import { DoctorService } from 'src/app/service/doctor.service';
import {Location} from '@angular/common';
import { AuthenticationService } from 'src/app/service/authentication.service';
@Component({
  selector: 'app-display-alldoctors',
  templateUrl: './display-alldoctors.component.html',
  styleUrls: ['./display-alldoctors.component.css']
})
export class DisplayAlldoctorsComponent implements OnInit {

  doctor: Doctor[] = [];
  filteredDoctors: Doctor[] = [];

  selectedGender: string = '';
  selectedSpecialization: string = '';
  searchName: string = '';

  genders: string[] = ['Male', 'Female', 'Other'];
  specializations: string[] = [];

  loggedInUserRole:string='';
  constructor(private doctorService: DoctorService,private location:Location,private authService:AuthenticationService ) {}

  ngOnInit(): void {
     const userRole= this.authService.getUserRole();
     if(userRole!=null)
     {
      this.loggedInUserRole=userRole;
     }
    this.loadAllDoctors();
  }
 
  loadAllDoctors(): void {
    this.doctorService.getAllDoctors().subscribe({
      next: (response) => {
        this.doctor = response;
        this.filteredDoctors = response;

        // Populate unique specializations from the list
        this.specializations = [...new Set(response.map(doc => doc.specialization))];
      },
      error: (err) => {
        console.error("Error fetching doctors", err);
      }
    });
  }

  // Filter by gender
  filterByGender(): void {
    if (this.selectedGender === '') {
      this.loadAllDoctors();
    } else {
      this.doctorService.getDoctorsByGender(this.selectedGender).subscribe({
        next: (response) => this.filteredDoctors = response,
        error: (err) => console.error('Error filtering by gender', err)
      });
    }
  }

  // Filter by specialization
  filterBySpecialization(): void {
    if (this.selectedSpecialization === '') {
      this.loadAllDoctors();
    } else {
      this.doctorService.getDoctorsBySpecialization(this.selectedSpecialization).subscribe({
        next: (response) => this.filteredDoctors = response,
        error: (err) => console.error('Error filtering by specialization', err)
      });
    }
  }

  // Search by doctor name
  searchByName(): void {
    if (this.searchName.trim() === '') {
      this.loadAllDoctors();
    } else {
      this.doctorService.getDoctorsByName(this.searchName.trim()).subscribe({
        next: (response) => this.filteredDoctors = response,
        error: (err) => console.error('Error searching by name', err)
      });
    }
  }

  clearFilters(): void {
    this.selectedGender = '';
    this.selectedSpecialization = '';
    this.searchName = '';
    this.loadAllDoctors();
  }

  goBack(): void {
    this.location.back(); 
  }
}

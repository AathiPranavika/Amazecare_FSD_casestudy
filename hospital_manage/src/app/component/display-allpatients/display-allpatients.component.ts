
import { Component, OnInit } from '@angular/core';
import { Patient } from 'src/app/model/Patient';
import { PatientService } from 'src/app/service/patient.service';
import { Location } from '@angular/common';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-display-allpatients',
  templateUrl: './display-allpatients.component.html',
  styleUrls: ['./display-allpatients.component.css']
})
export class DisplayAllpatientsComponent implements OnInit {

  patients: Patient[] = [];
  filteredPatients: Patient[] = [];

  selectedGender: string = '';
  selectedBloodGroup: string = '';
  searchName: string = '';
  loggedInUserRole:string='';

  genders: string[] = ['Male', 'Female', 'Other'];
  bloodGroups: string[] = ['A+', 'A-', 'B+', 'B-', 'O+', 'O-', 'AB+', 'AB-'];

  constructor(private patientService: PatientService, private location: Location,private authService:AuthenticationService) {}

  ngOnInit(): void {
    const userRole= this.authService.getUserRole();
     if(userRole!=null)
     {
      this.loggedInUserRole=userRole;
     }
    this.loadAllPatients();
  }

  loadAllPatients(): void {
    this.patientService.getAllPatients().subscribe({
      next: (response) => {
        this.patients = response;
        this.filteredPatients = response;
      },
      error: (err) => console.error("Error fetching patients", err)
    });
  }

  filterByGender(): void {
    if (this.selectedGender === '') {
      this.loadAllPatients();
    } else {
      this.filteredPatients = this.patients.filter(p => p.user.gender === this.selectedGender);
    }
  }

  filterByBloodGroup(): void {
    if (this.selectedBloodGroup === '') {
      this.loadAllPatients();
    } else {
      this.filteredPatients = this.patients.filter(p => p.bloodGroup === this.selectedBloodGroup);
    }
  }

  searchByName(): void {
    if (this.searchName.trim() === '') {
      this.loadAllPatients();
    } else {
      const name = this.searchName.trim().toLowerCase();
      this.filteredPatients = this.patients.filter(p => p.user.name.toLowerCase().includes(name));
    }
  }

  clearFilters(): void {
    this.selectedGender = '';
    this.selectedBloodGroup = '';
    this.searchName = '';
    this.loadAllPatients();
  }

  goBack(): void {
    this.location.back();
  }
}

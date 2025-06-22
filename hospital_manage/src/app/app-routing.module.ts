import { NgModule } from '@angular/core';
import { ExtraOptions, RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './component/login/login.component';
import { AdminDashBoardComponent } from './component/admin-dashboard/admin-dashboard.component';
import { DoctorDashboardComponent } from './component/doctor-dashboard/doctor-dashboard.component';
import { PatientDashboardComponent } from './component/patient-dashboard/patient-dashboard.component';
import { DoctorRegisterComponent } from './component/doctor-register/doctor-register.component';
import { PatientRegisterComponent } from './component/patient-register/patient-register.component';
import { RegistrationComponent } from './component/registration/registration.component';
import { AdminRegisterComponent } from './component/admin-register/admin-register.component';
import { HomeComponent } from './component/home/home.component';

import { UnauthorizedComponent } from './component/unauthorized/unauthorized.component'; 
import { RoleGuard } from './Interceptors/role.guard';
import { UpdateUserComponent } from './component/update-user/update-user.component';
import { ViewFulluserComponent } from './component/view-fulluser/view-fulluser.component';
import { DisplayAllusersComponent } from './component/display-allusers/display-allusers.component';
import { UpdatePatientComponent } from './component/update-patient/update-patient.component';
import { UpdateDoctorComponent } from './component/update-doctor/update-doctor.component';
import { UpdateAdminComponent } from './component/update-admin/update-admin.component';
import { DisplayAlldoctorsComponent } from './component/display-alldoctors/display-alldoctors.component';
import { DisplayAllpatientsComponent } from './component/display-allpatients/display-allpatients.component';
import { BookAppointmentComponent } from './component/book-appointment/book-appointment.component';
import { ViewAppointmentComponent } from './component/view-appointment/view-appointment.component';
import { UpdateAppointmentComponent } from './component/update-appointment/update-appointment.component';
import { ViewFullappointmentComponent } from './component/view-fullappointment/view-fullappointment.component';
import { PatientAppointmentsComponent } from './component/patient-appointments/patient-appointments.component';
import { DoctorAppointmentsComponent } from './component/doctor-appointments/doctor-appointments.component';
import { AddMedicalrecordsComponent } from './component/add-medicalrecords/add-medicalrecords.component';
import { UpdateMedicalrecordsComponent } from './component/update-medicalrecords/update-medicalrecords.component';
import { ViewMedicalrecordsComponent } from './component/view-medicalrecords/view-medicalrecords.component';
import { AddPrescriptionComponent } from './component/add-prescription/add-prescription.component';
import { ViewPrescriptionComponent } from './component/view-prescription/view-prescription.component';
import { ManagePrescriptionComponent } from './component/manage-prescription/manage-prescription.component';
import { UpdatePrescriptionComponent } from './component/update-prescription/update-prescription.component';
import { ViewAllMedicalrecordsComponent } from './component/view-all-medicalrecords/view-all-medicalrecords.component';
import { PatientMessagingComponent } from './component/patient-messaging/patient-messaging.component';
import { DoctorMessagingComponent } from './component/doctor-messaging/doctor-messaging.component';
import { ForgotPasswordComponent } from './component/forgot-password/forgot-password.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  //routes protected with guards for role specific access
  { 
    path: 'admin-dashboard', 
    component: AdminDashBoardComponent,
    //calls RoleGuard.canActivate() 
    canActivate: [RoleGuard], 
    data: { expectedRoles: 'ADMIN' },
    
  },
  { 
    path: 'doctor-dashboard', 
    component: DoctorDashboardComponent, 
    canActivate: [RoleGuard], 
    data: { expectedRoles: 'DOCTOR' }
  },
  { 
    path: 'patient-dashboard', 
    component: PatientDashboardComponent, 
    canActivate: [RoleGuard], 
    data: { expectedRoles: 'PATIENT' }
  },
  // Public routes without guards
  { path: 'doctor-register/:id', component: DoctorRegisterComponent },
  { path: 'patient-register', component: PatientRegisterComponent },
  { path: 'admin-register/:id', component: AdminRegisterComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'home', component: HomeComponent },
  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: 'view-fulluser/:id', component: ViewFulluserComponent },
  {path:'display-allusers',component:DisplayAllusersComponent},
  {path:'update-user/:id',component:UpdateUserComponent},
    {path:'update-patient/:id',component:UpdatePatientComponent},
    {path:'update-doctor/:id',component:UpdateDoctorComponent},
    {path:'update-admin/:id',component:UpdateAdminComponent},
   {path:'display-alldoctors',component:DisplayAlldoctorsComponent},
      {path:'display-allpatients',component:DisplayAllpatientsComponent},
      {path:'book-appointment',component:BookAppointmentComponent},
      {path:'view-appointment',component:ViewAppointmentComponent},
      {path:'view-fullappointment/:id',component:ViewFullappointmentComponent},
      {path:'update-appointment/:id',component:UpdateAppointmentComponent},
      {path:'patient-appointments/:id',component:PatientAppointmentsComponent},
      {path:'doctor-appointments/:id',component:DoctorAppointmentsComponent},
      {path:'add-medicalrecords/:appointmentId',component:AddMedicalrecordsComponent},
      {path:'update-medicalrecords/:appointmentId',component:UpdateMedicalrecordsComponent},
{ path: 'view-medicalrecords/:appointmentId', component: ViewMedicalrecordsComponent },
{path:'add-prescription/:appointmentId',component:AddPrescriptionComponent},
{path:'view-prescription/:appointmentId',component:ViewPrescriptionComponent},
{ path: 'manage-prescription/:appointmentId', component: ManagePrescriptionComponent },
{path:'update-prescription/:prescriptionId',component:UpdatePrescriptionComponent},
{path:'view-all-medicalrecord/:patientId',component:ViewAllMedicalrecordsComponent},
{ path: 'patient-messaging/:doctorId', component: PatientMessagingComponent },
{path:'doctor-messaging/:patientId',component:DoctorMessagingComponent},
{path:'forgot-password',component:ForgotPasswordComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
];

const routerOptions: ExtraOptions = {
  anchorScrolling: 'enabled',
  scrollOffset: [0, 70],  
  onSameUrlNavigation: 'reload', 
};
@NgModule({
  imports: [RouterModule.forRoot(routes, routerOptions)],

  exports: [RouterModule]
})
export class AppRoutingModule { }

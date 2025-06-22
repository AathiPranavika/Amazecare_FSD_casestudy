import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { RegistrationComponent } from './component/registration/registration.component';
import { LoginComponent } from './component/login/login.component';
import { HomeComponent } from './component/home/home.component';
import { AdminDashBoardComponent } from './component/admin-dashboard/admin-dashboard.component';
import { DoctorDashboardComponent } from './component/doctor-dashboard/doctor-dashboard.component';
import { PatientDashboardComponent } from './component/patient-dashboard/patient-dashboard.component';
import { DoctorRegisterComponent } from './component/doctor-register/doctor-register.component';
import { PatientRegisterComponent } from './component/patient-register/patient-register.component';
import { AdminRegisterComponent } from './component/admin-register/admin-register.component';
//interceptors
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './Interceptors/auth.interceptor';
import { UnauthorizedComponent } from './component/unauthorized/unauthorized.component';
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
import { ViewPrescriptionComponent } from './component/view-prescription/view-prescription.component';
import { ViewMedicalrecordsComponent } from './component/view-medicalrecords/view-medicalrecords.component';
import { AddPrescriptionComponent } from './component/add-prescription/add-prescription.component';
import { ManagePrescriptionComponent } from './component/manage-prescription/manage-prescription.component';
import { UpdatePrescriptionComponent } from './component/update-prescription/update-prescription.component';
import { ViewAllMedicalrecordsComponent } from './component/view-all-medicalrecords/view-all-medicalrecords.component';
import { PatientMessagingComponent } from './component/patient-messaging/patient-messaging.component';
import { DoctorMessagingComponent } from './component/doctor-messaging/doctor-messaging.component';
import { ForgotPasswordComponent } from './component/forgot-password/forgot-password.component';


@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    HomeComponent,
    AdminDashBoardComponent,
    DoctorDashboardComponent,
    PatientDashboardComponent,
    DoctorRegisterComponent,
    PatientRegisterComponent,
    AdminRegisterComponent,
    UnauthorizedComponent,
    UpdateUserComponent,
    ViewFulluserComponent,
    DisplayAllusersComponent,
    UpdatePatientComponent,
    UpdateDoctorComponent,
    UpdateAdminComponent,
    DisplayAlldoctorsComponent,
    DisplayAllpatientsComponent,
    BookAppointmentComponent,
    ViewAppointmentComponent,
    UpdateAppointmentComponent,
    ViewFullappointmentComponent,
    PatientAppointmentsComponent,
    DoctorAppointmentsComponent,
    AddMedicalrecordsComponent,
    UpdateMedicalrecordsComponent,
    ViewPrescriptionComponent,
    ViewMedicalrecordsComponent,
    AddPrescriptionComponent,
    ManagePrescriptionComponent,
    UpdatePrescriptionComponent,
    ViewAllMedicalrecordsComponent,
    PatientMessagingComponent,
    DoctorMessagingComponent,
    ForgotPasswordComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

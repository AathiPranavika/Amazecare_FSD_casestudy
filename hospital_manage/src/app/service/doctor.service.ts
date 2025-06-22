import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Doctor } from '../model/Doctor';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
baseURL = 'http://localhost:8080/api/doctors';

  constructor(private http:HttpClient) { 

  }

   addDoctorDetails(doctorDetails: Doctor): Observable<Doctor> {
    return this.http.post<Doctor>(`${this.baseURL}/register`, doctorDetails);
  }

  getAllDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.baseURL}/getAll`);
  }

  checkDoctorExistsByUserId(userId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseURL}/existsByUserId/${userId}`);
  }

  getDoctorByUserId(userId: number): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.baseURL}/byuserId/${userId}`);
  }

  updateDoctor(doctorId: number, doctorDetails: Doctor): Observable<Doctor> {
    console.log("doctor id"+doctorId)
    return this.http.put<Doctor>(`${this.baseURL}/update/${doctorId}`, doctorDetails);
  }

  getDoctorById(doctorId: number): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.baseURL}/deleteById/${doctorId}`);
  }

  getDoctorsByGender(gender: string): Observable<Doctor[]> {
  return this.http.get<Doctor[]>(`${this.baseURL}/byGender/${gender}`);
}

getDoctorsBySpecialization(specialization: string): Observable<Doctor[]> {
  return this.http.get<Doctor[]>(`${this.baseURL}/bySpecialization/${specialization}`);
}


getDoctorsByName(name: string): Observable<Doctor[]> {
  return this.http.get<Doctor[]>(`${this.baseURL}/searchByName/${name}`);
}

getDoctorsByGenderAndSpecialization(gender: string, specialization: string): Observable<Doctor[]> {
  return this.http.get<Doctor[]>(`${this.baseURL}/filter/${gender}/${specialization}`);
}

sendMessageAsDoctor(messageDTO: any): Observable<any> {
  return this.http.post<any>(`${this.baseURL}/sendMessage`, messageDTO);
}

getMessagesBetweenDoctorAndPatient(doctorId: number, patientId: number): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseURL}/messages/${doctorId}/${patientId}`);
}


}

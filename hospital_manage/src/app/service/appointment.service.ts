import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Appointment } from '../model/Appointment';
import { Observable } from 'rxjs';
import { AppointmentDTO } from '../model/AppointmentDTO';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private baseURL = 'http://localhost:8080/api/appointments';

  constructor(private http: HttpClient) {}

  getAllAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseURL}/getAll`);
  }

  getAppointmentById(id: number): Observable<Appointment> {
    return this.http.get<Appointment>(`${this.baseURL}/getById/${id}`);
  }

  updateAppointment(id: number, updatedAppointment:AppointmentDTO): Observable<Appointment> {
    return this.http.put<Appointment>(`${this.baseURL}/update/${id}`, updatedAppointment);
  }

  cancelAppointment(id: number): Observable<Appointment> {
    return this.http.put<Appointment>(`${this.baseURL}/cancel/${id}`, {});
  }

  getAppointmentsByPatientId(patientId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseURL}/patient/${patientId}`);
  }

  getAppointmentsByDoctorId(doctorId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseURL}/doctor/${doctorId}`);
  }

  getAppointmentsByStatus(status: string): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.baseURL}/status/${status}`);
  }

  deleteAppointment(id: number): Observable<string> {
    return this.http.delete(`${this.baseURL}/delete/${id}`, { responseType: 'text' });
  }

  getAppointmentsByPatientAndDoctor(patientId: number, doctorId: number): Observable<Appointment[]> {
  return this.http.get<Appointment[]>(`${this.baseURL}/patient/${patientId}/doctor/${doctorId}`);
}
getAppointmentsByPatientAndStatus(patientId: number, status: string): Observable<Appointment[]> {
  return this.http.get<Appointment[]>(`${this.baseURL}/patient/${patientId}/status/${status}`);
}

getAppointmentsByDoctorAndStatus(doctorId: number, status: string): Observable<Appointment[]> {
  return this.http.get<Appointment[]>(`${this.baseURL}/patient/${doctorId}/status/${status}`);
}

}

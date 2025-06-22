import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prescription } from '../model/Prescription';
@Injectable({
  providedIn: 'root'
})
export class PrescriptionService {

  private baseUrl = 'http://localhost:8080/api/prescriptions';

  constructor(private http: HttpClient) {}

  addPrescription(prescription: Prescription): Observable<any> {
    return this.http.post(`${this.baseUrl}/add`, prescription);
  }

  getPrescriptionById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/getBy/${id}`);
  }

  updatePrescription(prescription: Prescription): Observable<any> {
    return this.http.put(`${this.baseUrl}/update`, prescription);
  }

  deletePrescription(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`, { responseType: 'text' });
  }


  getPrescriptionsByAppointmentId(appointmentId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/appointment/${appointmentId}`);
  }
}

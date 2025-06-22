import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MedicalRecord } from '../model/MedicalRecord';

@Injectable({
  providedIn: 'root'
})
export class MedicalrecordsService {

  private baseUrl = 'http://localhost:8080/api/medicalRecords';

  constructor(private http: HttpClient) {}

  createMedicalRecord(recordDTO: MedicalRecord): Observable<MedicalRecord> {
    return this.http.post<MedicalRecord>(`${this.baseUrl}/create`, recordDTO);
  }

  getMedicalRecordById(recordId: number): Observable<MedicalRecord> {
    return this.http.get<MedicalRecord>(`${this.baseUrl}/getbyid/${recordId}`);
  }

  getMedicalRecordByAppointmentId(appointmentId: number): Observable<MedicalRecord> {
    return this.http.get<MedicalRecord>(`${this.baseUrl}/byAppointment/${appointmentId}`);
  }

  getMedicalRecordsByPatientId(patientId: number): Observable<MedicalRecord[]> {
    return this.http.get<MedicalRecord[]>(`${this.baseUrl}/byPatient/${patientId}`);
  }

  updateMedicalRecord(medicalRecordId:number,recordDTO: MedicalRecord): Observable<MedicalRecord> {
console.log("updating medRecord", recordDTO);
    return this.http.put<MedicalRecord>(`${this.baseUrl}/update/${medicalRecordId}`, recordDTO);
  }

  
}

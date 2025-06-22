import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Patient } from '../model/Patient';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  baseURL = 'http://localhost:8080/api/patients';
  
    constructor(private http:HttpClient) { 
  
    }
  
    addPatientDetails(patientDetails:Patient):Observable<Patient>
    {
      return this.http.post<Patient>(this.baseURL+"/register",patientDetails);
    }
  

    checkPatientExistsByUserId(id:Number):Observable<Boolean>
    {
      return this.http.get<Boolean>(this.baseURL+"/exists/"+id);
    }

getPatientDetailsByUserId(userId: number): Observable<Patient> {
  return this.http.get<Patient>(`${this.baseURL}/byuserId/${userId}`);
}

updatePatient(patientId: number, patientDetails: Patient): Observable<Patient> {
    return this.http.put<Patient>(`${this.baseURL}/update/${patientId}`, patientDetails);
  }

  getAllPatients(): Observable<Patient[]> {
  return this.http.get<Patient[]>(`${this.baseURL}/getall`);
}

getPatientsByBloodGroup(bloodGroup: string): Observable<Patient[]> {
  return this.http.get<Patient[]>(`${this.baseURL}/search/bloodgroup/${bloodGroup}`);
}

getPatientsByName(name: string): Observable<Patient[]> {
  return this.http.get<Patient[]>(`${this.baseURL}/search/name/${name}`);
}


bookAppointment(appointmentDTO: any): Observable<any> {
  return this.http.post<any>(`${this.baseURL}/bookAppointment`, appointmentDTO);
}

cancelAppointment(appointmentId: number): Observable<string> {
  return this.http.put<string>(`${this.baseURL}/cancelAppointment/${appointmentId}`, {});
}


getMedicalRecordsByPatientId(patientId: number): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseURL}/medicalRecords/${patientId}`);
}

sendMessageAsPatient(messageDTO: any): Observable<any> {
  return this.http.post<any>(`${this.baseURL}/sendMessage`, messageDTO);
}

getMessagesBetweenDoctorAndPatient(doctorId: number, patientId: number): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseURL}/messages/between/${doctorId}/${patientId}`);
}

}
 
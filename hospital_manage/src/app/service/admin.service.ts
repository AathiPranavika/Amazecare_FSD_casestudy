import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Admin } from '../model/Admin';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = 'http://localhost:8080/api/admins';

  constructor(private http: HttpClient) {}

  getAdminByUserId(userId: number): Observable<Admin> {
    return this.http.get<Admin>(`${this.baseUrl}/byuserId/${userId}`);
  }

  addAdminDetails(admin: Admin): Observable<Admin> {
    console.log(admin)
    return this.http.post<Admin>(`${this.baseUrl}/register/`,admin)
  }
  updateAdmin(adminId: number, admin: Admin): Observable<Admin> {
    return this.http.put<Admin>(`${this.baseUrl}/update/${adminId}`, admin);
  }

  checkAdminExistsByUserId(userId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/existsByUserId/${userId}`);
  }
}

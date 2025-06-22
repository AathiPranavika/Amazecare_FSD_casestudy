import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { Login } from '../model/Login';

interface LoginResponse {
  token: string;
}
@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  login(credentials: Login): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login/authenticate`, credentials).pipe(
      //tap->doing some operation with response without changing it
      tap(res => {
        console.log(res.token)
        localStorage.setItem('token', res.token);

        try {
          //jwtDecode() is a function provided by a third-party library
          const decoded: any = jwtDecode(res.token);
          console.log("token: " + JSON.stringify(decoded));
          if (decoded.role) {
            console.log(decoded.role)
            localStorage.setItem('role', decoded.role);
          }
           if (decoded.id) {
            console.log('User ID:', decoded.id);
            localStorage.setItem('userId', decoded.id.toString());
          }

          if(decoded.sub)
          {
             console.log(decoded.sub)
            localStorage.setItem('sub', decoded.sub);
          }
        } catch (error) {
          console.error('Token decoding failed:', error);
        }
      })
    );
  }
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('userId');
    localStorage.removeItem('sub');

  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (token && token.trim() !== '') {
      return true;
    }
    return false;
  }

  getUserRole(): string | null {
    return localStorage.getItem('role');
  }

  getUsername(): string {
  const username = localStorage.getItem('sub');
  if (username === null) {
    throw new Error("Username not found in localStorage");
  }
  return username;
}


   getUserId(): number {
  const id = localStorage.getItem('userId');
  if (id === null) {
    throw new Error("User ID not found in localStorage");
  }
  return Number(id);
}


}
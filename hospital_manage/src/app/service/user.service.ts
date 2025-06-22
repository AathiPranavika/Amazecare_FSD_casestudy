import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class UserService  {
 baseURL = 'http://localhost:8080/api/users';

  constructor(private http:HttpClient) { 

  }
  getAll():Observable<User[]>
  {
      return this.http.get<User[]>(this.baseURL+"/getAll");
  }

  deleteUserById(id:number)
  {
    return this.http.delete(this.baseURL+"/deleteById/"+id,{
        responseType: 'text'
    });

  }

  addUser(userDetails: User): Observable<string> {
  return this.http.post(this.baseURL + "/registration/new", userDetails, {
    responseType: 'text'
  });
}

getUserById(id:number):Observable<User>
{
  
  return this.http.get<User>(this.baseURL+"/getById/"+id);
}

updateUser(id: number, userDetails: User): Observable<User> {
  return this.http.put<User>(`${this.baseURL}/update/${id}`, userDetails);
}

//reset password
resetPassword(email: string, newPassword: string):Observable<String> {
  const data = { email, newPassword };
  return this.http.put(`${this.baseURL}/reset-password`, data,{
        responseType: 'text'
  });
}


}

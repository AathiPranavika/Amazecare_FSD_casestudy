import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Login } from 'src/app/model/Login';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  error: string | null = null;
  loginData!:Login;

  constructor(private auth: AuthenticationService, private router: Router) {}

  loginUser(loginDetails:Login) {
    this.loginData=loginDetails;
    console.log(this.loginData.password+" "+this.loginData.userName)
    this.auth.login(this.loginData).subscribe({
      next: () => {
        const role = this.auth.getUserRole();
          console.log('Retrieved Role:', role); 
          console.log('Token:', this.auth.getToken()); 
        if (role == 'ADMIN') {
          this.router.navigate(['/admin-dashboard']);
        } else if (role == 'DOCTOR') {
          this.router.navigate(['/doctor-dashboard']);
        } else if (role == 'PATIENT') {
          this.router.navigate(['/patient-dashboard']);
        } else {
          alert("Invalid credentials")
          this.router.navigate(['/unauthorized']);
        }
      },
       error: (err) => {
        alert("Invalid credentials")
      console.error('Login failed:', err);
       }
    });
  }

  goBack()
  {
    this.router.navigate(['/home']);

  }
}

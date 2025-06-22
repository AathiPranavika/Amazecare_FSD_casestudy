import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './service/authentication.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    isDropdownOpen = false;
  loggedInUserId: number | null = null;

  constructor(private authService: AuthenticationService) {}

  ngOnInit(): void {
    this.loggedInUserId = this.authService.getUserId();
    console.log(this.loggedInUserId)
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  closeDropdown() {
    this.isDropdownOpen = false;
  }

  logout() {
  this.isDropdownOpen = false;

  try {
    const userId = this.authService.getUserId();
    this.authService.logout();
    alert("Logged out successfully");
  } 
  catch (err) {
    alert("No user logged In");
  }
}

}

// role.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';

@Injectable({
  providedIn: 'root'
})

//CanActivate-> angular interface used to control access to routes in Angular applications.
export class RoleGuard implements CanActivate {

  constructor(private auth: AuthenticationService, private router: Router) {}
 //ActivatedRouteSnapshot->It represents all info about current route that the user is trying to navigate to.
 //canActivate() is a CanActivate interface method used to determine whether the user is allowed to navigate to a specific route.
  canActivate(route: ActivatedRouteSnapshot): boolean {
    //role allowed in routing module
    const expectedRoles: string = route.data['expectedRoles'];
    //loggedin userRole
    const userRole = this.auth.getUserRole();

    if (userRole && expectedRoles.includes(userRole)) {
      return true;
    } else {
            console.error(`Access denied - Required roles: [${expectedRoles}], but user role is: ${userRole}`);
      this.router.navigate(['/unauthorized']);
      return false;
    }
  }
}

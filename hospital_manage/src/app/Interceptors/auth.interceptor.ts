import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from '../service/authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthenticationService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.auth.getToken();

    if (token) {
      request = request.clone({
        headers: request.headers.set('Authorization', 'Bearer ' + token)
      });
    }

    //next.handle(request) → actually sends the request to the server.
    //.pipe(catchError(...)) → waits for any error response (like 400, 500) from backend and handles it.
    return next.handle(request).pipe(
      //catchError->to catch backend errors.
      //this error obj contains->msg,status,url
      catchError((error: HttpErrorResponse) => {
        let errorMsg = 'Something went wrong';

        const rawError = typeof error.error === 'string' ? error.error : JSON.stringify(error.error);

        //UK6dotkott2kjsp8vw4d0m25fb7->The name of your unique constraint on the email field.
        if (rawError.includes('Duplicate entry') && rawError.includes('UK6dotkott2kjsp8vw4d0m25fb7')) {
          errorMsg = 'Email already exists';
        }
        else if (rawError.includes('Duplicate entry') && rawError.includes('UKn14mnjfh0j5psw8omlegp1d68')) {
        errorMsg = 'Contact number already exists';
        }
        else if (rawError.includes('dateOfBirth') && rawError.includes('must be a past date')) {
        errorMsg = 'Date of birth must be a past date';
        }
        else if (rawError.includes('Duplicate entry') && rawError.includes('UKhuj3mnt7e1f1dwghu6g9nktfe')) {
        errorMsg = 'Admin code already exists';
        }
        
       else if (error.status === 0) {
          errorMsg = 'Server not reachable';
        } 
        else if (typeof error.error === 'string') {
          errorMsg = error.error;
        }
        else if (error.error?.message) {
          errorMsg = error.error.message;//access backend error msg
        } 

        return throwError(() => new Error(errorMsg));
      })
    );
  }
}

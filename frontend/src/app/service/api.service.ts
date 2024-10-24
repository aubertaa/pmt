import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';

export interface LoginResponse {
    userName: string;
    email: string;
    password: string;
}

export interface RegisterResponse {
  id: string;
}

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private apiUrl =
    'http://localhost:8081/api';
  constructor(private httpClient: HttpClient) {}

  catchError(error: HttpErrorResponse) {
    //alert(error.error.error);
    return throwError(() => error);
  }

  registerUser(username: string, email: string, password: string) {
    return this.httpClient
      .post<RegisterResponse>(`${this.apiUrl}/user`, {
        userName: username,
        email: email,
        password: password,
      })
      .pipe(catchError(this.catchError));
  }

  loginUser(username: string) {
    let queryParams = new HttpParams()
      .set('userName', `${username}`);

    return this.httpClient
      .get<LoginResponse>(`${this.apiUrl}/user`, {
        params: queryParams,
      })
      .pipe(catchError(this.catchError));
  }
}

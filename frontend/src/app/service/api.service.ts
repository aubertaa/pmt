import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Project } from './project.service';

export interface LoginResponse {
  userId: string;
  userName: string;
  email: string;
  password: string;
}

export interface ProjectRequest {
  project: Project;
  userId: number;
}

export interface CreatedResponse {
  id: number;
}

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  getProjects(userId: number) {
    return this.httpClient
      .get<Project[]>(`${this.apiUrl}/projects?userId=${userId}`)
      .pipe(catchError(this.catchError));
  }
  private apiUrl = 'http://localhost:8081/api';
  constructor(private httpClient: HttpClient) {}

  deleteProject(id: number) {
    return this.httpClient
      .delete<CreatedResponse>(`${this.apiUrl}/project`, {
        params: new HttpParams().set('id', id.toString()),
      })
      .pipe(catchError(this.catchError));
  }

  addProject(projectName: string, description: string, startDate: Date) {
    let loggedInUserId = localStorage.getItem('loggedInUserId') ?? '';

    const projectRequest: ProjectRequest = {
      project: {
        projectName: projectName,
        description: description,
        startDate: startDate,
        id: 0,
      },
      userId: parseInt(loggedInUserId),
    };

    return this.httpClient
      .post<CreatedResponse>(`${this.apiUrl}/project`, projectRequest)
      .pipe(catchError(this.catchError));
  }

  catchError(error: HttpErrorResponse) {
    //alert(error.error.error);
    return throwError(() => error);
  }

  registerUser(username: string, email: string, password: string) {
    return this.httpClient
      .post<CreatedResponse>(`${this.apiUrl}/user`, {
        userName: username,
        email: email,
        password: password,
      })
      .pipe(catchError(this.catchError));
  }

  loginUser(username: string) {
    let queryParams = new HttpParams().set('userName', `${username}`);

    return this.httpClient
      .get<LoginResponse>(`${this.apiUrl}/user`, {
        params: queryParams,
      })
      .pipe(catchError(this.catchError));
  }
}

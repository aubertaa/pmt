import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
  HttpResponse,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Project } from './project.service';
import { User } from './auth.service';
import { Task, TaskRequest } from './task.service';

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

  private apiUrl = 'http://localhost:8081/api';
  constructor(private httpClient: HttpClient) { }

  changeRole(userId: number, projectId: number, newRole: string) {

    const params = new HttpParams()
      .set('projectId', projectId)
      .set('userId', userId)
      .set('role', newRole);

    return this.httpClient.post<unknown>(`${this.apiUrl}/project/changeRole`, null,
      { params });
  }

  addProjectMember (userId: number, projectId: number) {
    const params = new HttpParams()
      .set('projectId', projectId)
      .set('userId', userId);

    return this.httpClient
      .post<unknown>(`${this.apiUrl}/project/addMember`, null, { params })
      .pipe(catchError(this.catchError));
  }

  getRoles () {
    return this.httpClient
      .get<string[]>(`${this.apiUrl}/roles`)
      .pipe(catchError(this.catchError));
  }

  getPriorities () {
    return this.httpClient
      .get<string[]>(`${this.apiUrl}/priorities`)
      .pipe(catchError(this.catchError));
  }

  getStatuses () {
    return this.httpClient
      .get<string[]>(`${this.apiUrl}/statuses`)
      .pipe(catchError(this.catchError));
  }

  deleteProject (id: number) {
    return this.httpClient
      .delete<CreatedResponse>(`${this.apiUrl}/project`, {
        params: new HttpParams().set('id', id.toString()),
      })
      .pipe(catchError(this.catchError));
  }

  addProject (projectName: string, description: string, startDate: Date) {
    let loggedInUserId = localStorage.getItem('loggedInUserId') ?? '';

    const projectRequest: ProjectRequest = {
      project: {
        projectName: projectName,
        description: description,
        startDate: startDate,
        id: 0
      },
      userId: parseInt(loggedInUserId),
    };

    return this.httpClient
      .post<CreatedResponse>(`${this.apiUrl}/project`, projectRequest)
      .pipe(catchError(this.catchError));
  }

  getProjects (userId: number) {
    return this.httpClient
      .get<Project[]>(`${this.apiUrl}/projects?userId=${userId}`)
      .pipe(catchError(this.catchError));
  }

  catchError (error: HttpErrorResponse) {
    //alert(error.error.error);
    return throwError(() => error);
  }

  registerUser (username: string, email: string, password: string) {
    return this.httpClient
      .post<CreatedResponse>(`${this.apiUrl}/user`, {
        userName: username,
        email: email,
        password: password,
      })
      .pipe(catchError(this.catchError));
  }

  loginUser (username: string) {
    let queryParams = new HttpParams().set('userName', `${username}`);

    return this.httpClient
      .get<LoginResponse>(`${this.apiUrl}/user`, {
        params: queryParams,
      })
      .pipe(catchError(this.catchError));
  }

  getUsers () {
    return this.httpClient
      .get<User[]>(`${this.apiUrl}/users`)
      .pipe(catchError(this.catchError));
  }

  
  createTask (name: string, description: string, priority: string, status: string, dueDate: Date, projectId: number) {

    const taskRequest: TaskRequest = {
      task: {
        id:0,
        name: name,
        description: description,
        priority: priority,
        dueDate: dueDate,
        status: status
      },
      projectId: projectId,
    };

    return this.httpClient
      .post<CreatedResponse>(`${this.apiUrl}/task`, taskRequest)
      .pipe(catchError(this.catchError));

    }

  assignTaskToUser (userId: number, taskId: number) {
    const params = new HttpParams()
      .set('taskId', taskId)
      .set('userId', userId);

    return this.httpClient
      .post<unknown>(`${this.apiUrl}/task/assign`, null, { params })
      .pipe(catchError(this.catchError));
  }

  getTasks () {
    return this.httpClient
      .get<Task[]>(`${this.apiUrl}/tasks`)
      .pipe(catchError(this.catchError));
  }

}

import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Router } from '@angular/router';
import { ToastService } from './toast.service';
import { BehaviorSubject } from 'rxjs';

export interface User {
  userName: string;
  email: string;
  userId: number;
  notifications:boolean;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _isLoggedIn: boolean = false;
  private _password: string = '';

  usersSubject = new BehaviorSubject<User[]>([]);
  users$ = this.usersSubject.asObservable();

  public get isLoggedIn(): boolean {
    return this._isLoggedIn;
  }

  constructor(
    private apiService: ApiService,
    private router: Router,
    private toastService: ToastService
  ) {}

  login(username: string, password: string) {
    console.log('login as: ' + username + '/' + password);
    this._password = password;
    this.apiService.loginUser(username).subscribe({
      next: (res) => {
        console.log('res: ' + res);

        const resPassword = res.password;
        console.log('resPassword: ' + resPassword);

        try {
          if (resPassword === password) {
            this._isLoggedIn = true;
            localStorage.setItem('isLoggedIn', 'true');
            localStorage.setItem('loggedInUserId', res.userId);
            this.toastService.addToast('Login successful !');
            this.router.navigate(['/']);
          } else {
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('loggedInUserId');
            this.toastService.addToast('Login failed !');
            console.log('Login failed !');
          }
        } catch (error) {
          localStorage.removeItem('isLoggedIn');
          localStorage.removeItem('loggedInUserId');
          this.toastService.addToast('Login failed !');
          console.log('Login failed !');
        }
      },
      error: (error) => {
        this.toastService.addToast('Login failed !');
        console.log('Login failed !');
        localStorage.removeItem('loggedInUserId');
        localStorage.removeItem('isLoggedIn');
      },
    });
  }

  register(username: string, email: string, password: string) {
    console.log('register as: ' + username + '/' + email + '/' + password);

    this.apiService.registerUser(username, email, password).subscribe({
      next: (res) => {
        console.log(res);
        this.toastService.addToast('Registration successful !');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.toastService.addToast('Registration failed, try another email !');
        console.log('Registration failed !');
        localStorage.removeItem('isLoggedIn');
      },
    });
  }

  logout() {
    this._isLoggedIn = false;
    localStorage.removeItem('isLoggedIn');
  }

  isAuthenticated() {
    return localStorage.getItem('isLoggedIn') == 'true';
  }

  updateUserNotificationSetting (user: User, notificationsActive: boolean) {
    this.apiService.updateUserNotificationSetting(user, notificationsActive).subscribe({
      next: (res) => {
        console.log('res: ' + res);
          this.toastService.addToast('Notification set to ' + notificationsActive ? 'active' : 'inactive' + ' !');
          this.apiService.getUsers();
      },
      error: (error) => {
        this.toastService.addToast('Notification status update failed!');
      },
    });
  }
  
  getUsers() {
    this.apiService.getUsers().subscribe({
      next: (res) => {
        console.log('res: ' + res);
        try {
          this.usersSubject.next(res); // Update the users Subject with the fetched roles
          this.toastService.addToast('Users fetched !');
        } catch (error) {
          this.toastService.addToast('Users not fetched !');
        }
      },
      error: (error) => {
        this.toastService.addToast('Users not fetched !');
      },
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  isLoggedIn: boolean = this.authService.isAuthenticated();

  constructor(private authService: AuthService, private router: Router) {
    console.log('isLoggedIn on home: ' + this.isLoggedIn);
    this.isLoggedIn = this.authService.isLoggedIn;
  }
  ngOnInit(): void {
    this.isLoggedIn = this.authService.isAuthenticated();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}

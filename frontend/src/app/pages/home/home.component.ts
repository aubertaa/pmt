import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { Project, ProjectService } from '../../service/project.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  isLoggedIn: boolean = this.authService.isAuthenticated();

  projects: Project[] = [];

  currentProjectName: string = '';
  currentProjectDescription: string = '';
  currentStartDate: Date = new Date();

  constructor(
    private authService: AuthService,
    private router: Router,
    private projectService: ProjectService
  ) {
    console.log('isLoggedIn on home: ' + this.isLoggedIn);
    this.isLoggedIn = this.authService.isLoggedIn;
  }

  onAddProject (form: NgForm) {
    //valider tous les champs
    form.form.markAllAsTouched();

    if (form.invalid) {
      return;
    }

    this.projectService.addProject(
      this.currentProjectName,
      this.currentProjectDescription,
      this.currentStartDate
    );

    form.reset();
  }

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isAuthenticated();
    if (this.isLoggedIn) {
      const loggedInUserId = localStorage.getItem('loggedInUserId');
      if (loggedInUserId) {
        this.projectService.getProjects(parseInt(loggedInUserId));
        this.projectService.projects$.subscribe((projects) => {
          this.projects = projects;
        });
      }
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}

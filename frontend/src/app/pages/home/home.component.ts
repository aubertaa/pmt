import { Component, OnInit } from '@angular/core';
import { AuthService, User } from '../../service/auth.service';
import { Project, ProjectService } from '../../service/project.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { TaskService } from '../../service/task.service';
import { Observable } from 'rxjs';

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

  priorities$: Observable<string[]>;
  statuses$: Observable<string[]>;
  roles$: Observable<string[]>;
  users$: Observable<User[]>;

  constructor(
    private authService: AuthService,
    private taskService: TaskService,
    private router: Router,
    private projectService: ProjectService
  ) {
    console.log('isLoggedIn on home: ' + this.isLoggedIn);
    this.isLoggedIn = this.authService.isLoggedIn;
    this.roles$ = this.projectService.roles$;
    this.users$ = this.authService.users$;
    this.priorities$ = this.taskService.priorities$;
    this.statuses$ = this.taskService.statuses$;
  }

  onAddProject (form: NgForm) {

    const formValues = form.value;
    console.log(formValues);
    //valider tous les champs
    form.form.markAllAsTouched();

    if (form.invalid) {
      return;
    }

    this.projectService.addProject(
      formValues.currentProjectName,
      formValues.currentProjectDescription,
      formValues.currentStartDate
    );

    form.reset();
  }

  ngOnInit(): void {
    this.isLoggedIn = this.authService.isAuthenticated();
    this.projectService.getRoles();
    this.taskService.getPriorities();
    this.taskService.getStatuses();
    this.authService.getUsers();
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

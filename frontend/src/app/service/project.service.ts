import { Injectable } from '@angular/core';
import { ToastService } from './toast.service';
import { ApiService } from './api.service';
import { BehaviorSubject } from 'rxjs';
import { User } from './auth.service';

export interface Project {
  projectName: string;
  description: string;
  startDate: Date;
  id: number;
  userRole?: string;
  members?: ProjectMember[];
}

export interface ProjectMember {
  user?: User,
  project?: Project,
  role?: string
  id: {
    userId: number,
    projectId: number
  }
}

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  projectsSubject = new BehaviorSubject<Project[]>([]);
  projects$ = this.projectsSubject.asObservable();
  rolesSubject = new BehaviorSubject<string[]>([]);
  roles$ = this.rolesSubject.asObservable();

  constructor(
    private toastService: ToastService,
    private apiService: ApiService
  ) { }

  addProject (projectName: string, description: string, startDate: Date) {
    this.apiService.addProject(projectName, description, startDate).subscribe({
      next: (res) => {
        console.log('res: ' + res);

        try {
          this.getProjects(parseInt(localStorage.getItem('loggedInUserId') ?? "0"));
          this.toastService.addToast('Project ' + projectName + ' added !');
        } catch (error) {
          this.toastService.addToast(
            'Project ' + projectName + ' not created !'
          );
        }
      },
      error: (error) => {
        this.toastService.addToast('Project ' + projectName + ' not created !');
      },
    });
  }

  addMember (userId: number, projectId: number) {
    this.apiService.addProjectMember(userId, projectId).subscribe({
      next: () => {
        this.getProjects(parseInt(localStorage.getItem('loggedInUserId') ?? "0"));
        this.toastService.addToast('User ' + userId + ' added to project !');
      },
      error: () => {
        this.toastService.addToast(
          'User ' + userId + ' not added to project !');
      },
    });
  }

  changeRole (userId: number, projectId: number, newRole: string) {
    this.apiService.changeRole(userId, projectId, newRole).subscribe({
      next: () => {
        this.getProjects(parseInt(localStorage.getItem('loggedInUserId') ?? "0"));
        this.toastService.addToast('Role of ' + userId + ' changed !');
      },
      error: () => {
        this.toastService.addToast(
          'Role of ' + userId + ' not changed !');
      },
    });
  }

  getProjects (userId: number) {
    this.apiService.getProjects(userId).subscribe({
      next: (res) => {
        console.log('res: ' + res);
        try {
          this.projectsSubject.next(res);
          this.toastService.addToast('Projects fetched !');
        } catch (error) {
          this.toastService.addToast('Projects not fetched !');
        }
      },
      error: (error) => {
        this.toastService.addToast('Projects not fetched !');
      },
    });
  }

  getRoles () {
    this.apiService.getRoles().subscribe({
      next: (res) => {
        console.log('res: ' + res);
        try {
          this.rolesSubject.next(res); // Update the rolesSubject with the fetched roles
          this.toastService.addToast('Roles fetched !');
        } catch (error) {
          this.toastService.addToast('Roles not fetched !');
        }
      },
      error: (error) => {
        this.toastService.addToast('Roles not fetched !');
      },
    });
  }

  deleteProject (id: number) {
    const currentProjects = this.projectsSubject.value;
    const project = currentProjects.find((x) => x.id === id);

    if (!project) {
      this.toastService.addToast(`Project not found!`);
      return;
    }

    this.apiService.deleteProject(id).subscribe({
      next: () => {
        this.getProjects(parseInt(localStorage.getItem('loggedInUserId') ?? "0"));
        this.toastService.addToast(
          'Project ' + project.projectName + ' deleted !'
        );
      },
      error: (error) => {
        this.toastService.addToast(
          'Project ' + project.projectName + ' not deleted !'
        );
      },
    });
  }
}

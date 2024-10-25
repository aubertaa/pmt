import { Injectable } from '@angular/core';
import { ToastService } from './toast.service';
import { ApiService } from './api.service';
import { BehaviorSubject } from 'rxjs';

export interface Project {
  projectName: string;
  description: string;
  startDate: Date;
  id: number;
}

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  projectsSubject = new BehaviorSubject<Project[]>([]);
  projects$ = this.projectsSubject.asObservable();

  constructor(
    private toastService: ToastService,
    private apiService: ApiService
  ) {}

  addProject(projectName: string, description: string, startDate: Date) {
    this.apiService.addProject(projectName, description, startDate).subscribe({
      next: (res) => {
        console.log('res: ' + res);

        try {
          const newProject: Project = {
            projectName,
            description,
            startDate,
            id: res as unknown as number
          };
          const updatedProjects = [...this.projectsSubject.value, newProject];
          this.projectsSubject.next(updatedProjects);
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

  getProjects(userId: number) {
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

  deleteProject(id: number) {
    const currentProjects = this.projectsSubject.value;
    const project = currentProjects.find((x) => x.id === id);

    if (!project) {
      this.toastService.addToast(`Project not found!`);
      return;
    }

    this.apiService.deleteProject(id).subscribe({
      next: () => {
        const updatedProjects = currentProjects.filter((x) => x.id !== id);
        this.projectsSubject.next(updatedProjects);
        this.toastService.addToast('Project ' + project.projectName + ' deleted !');
      },
      error: (error) => {
        this.toastService.addToast('Project ' + project.projectName + ' not deleted !');
      },
    });
  }
}

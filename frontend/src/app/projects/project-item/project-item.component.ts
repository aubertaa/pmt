import { Component, Input } from '@angular/core';
import { Project, ProjectService } from '../../service/project.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-project-item',
  templateUrl: './project-item.component.html',
  styleUrl: './project-item.component.css',
})
export class ProjectItemComponent {
  @Input() project?: Project;

  constructor(private projectService: ProjectService, private router: Router) {
    console.log(this.router.url);
  }

  onDeleteProject(id: number) {
    console.log("deleting project with id: " + id);
    this.projectService.deleteProject(id);
  }

  onClickItem(clicked_project: Project) {
    //TODO
  }
}

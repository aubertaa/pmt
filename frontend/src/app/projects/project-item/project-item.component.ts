import { Component, Input, OnInit } from '@angular/core';
import { Project, ProjectService } from '../../service/project.service';
import { Router } from '@angular/router';
import { AuthService, User } from '../../service/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-project-item',
  templateUrl: './project-item.component.html',
  styleUrl: './project-item.component.css',
})
export class ProjectItemComponent implements OnInit {

  @Input() project?: Project;
  showInvitePopin: boolean = false;
  showMembersPopin = false;
  loggedInUserId = parseInt(localStorage.getItem('loggedInUserId') ?? "0");

  roles$: Observable<string[]>;
  users$: Observable<User[]>;

  constructor(private projectService: ProjectService,
    private authService: AuthService,
    private router: Router) {
    console.log(this.router.url);
    this.roles$ = this.projectService.roles$;
    this.users$ = this.authService.users$;
  }

  onSeeMembers(projectId: number) {
      this.showMembersPopin = true;
  }

  onChangeRole (userId: number, project: Project, event: Event) {
    const newRole = (event.target as HTMLSelectElement).value;
    console.log('changed role');
    this.projectService.changeRole(userId, project.id, newRole);
  }

  onAddMember (user: User, project: Project) {
    console.log('line clicked' + user.toString());
    this.projectService.addMember(user.userId, project.id);
  }

  isUserNotMember(project: Project, user: User): boolean {
  return !project.members?.some(member => member.id.userId === user.userId);
}

  onDeleteProject (id: number) {
    console.log("deleting project with id: " + id);
    this.projectService.deleteProject(id);
  }

  onClickItem (clicked_project: Project) {
    //TODO
  }
  onInviteUsers (event: Event, projectId: number) {
    event.stopPropagation();
    this.showInvitePopin = true;
      }

  closeInvitePopin () {
    this.showInvitePopin = false;
  }

  closeMembersPopin () {
    this.showMembersPopin = false;
  }

  ngOnInit (): void {
    this.projectService.getRoles();
    this.authService.getUsers();
  }

}

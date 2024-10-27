import { Component, Input, OnInit } from '@angular/core';
import { Project, ProjectService } from '../../service/project.service';
import { Router } from '@angular/router';
import { AuthService, User } from '../../service/auth.service';
import { Observable, map } from 'rxjs';
import { Task, TaskService } from '../../service/task.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-project-item',
  templateUrl: './project-item.component.html',
  styleUrl: './project-item.component.css',
})
export class ProjectItemComponent implements OnInit {

  @Input() project?: Project;
  @Input() roles: string[] = [];
  @Input() users: User[] = [];
  @Input() priorities: string[] = [];
  @Input() statuses: string[] = [];

  showInvitePopin: boolean = false;
  showAssignPopin: boolean = false;
  showMembersPopin: boolean = false;
  showTasks: boolean = false;
  showAddTaskForm: boolean = false;
  loggedInUserId = parseInt(localStorage.getItem('loggedInUserId') ?? "0");

  tasks$: Observable<Task[]>;

  taskName: string = '';
  taskDescription: string = '';
  taskPriority: string = 'MEDIUM';
  taskStatus: string = '';
  taskDueDate: Date = new Date();
  toAssignTask?: Task;


  constructor(private projectService: ProjectService,
    private taskService: TaskService,
    private authService: AuthService,
    private router: Router) {
    this.tasks$ = this.taskService.tasks$;
    console.log(this.router.url);
  }


  getUserName (user_id: number | undefined) {
    return this.users.find(user => user.userId === user_id)?.userName;
  }

  onAddTask (formTask: NgForm, projectId: number) {

    const taskformValues = formTask.value;
    console.log("taskformValues : " + taskformValues);

    //valider tous les champs
    formTask.form.markAllAsTouched();

    if (formTask.invalid) {
      return;
    }

    this.taskService.createTask(
      taskformValues.taskName,
      taskformValues.taskDescription,
      taskformValues.taskPriority,
      "TODO",
      taskformValues.taskDueDate,
      projectId
    );

    formTask.reset();
    this.closeTaskForm();
  }

  onSeeMembers () {
    this.showMembersPopin = true;
  }

  onShowAddTaskForm () {
    this.showAddTaskForm = true;
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

  onAssignTaskToUser (user: User) {
    console.log('line clicked' + user.toString());
    if (this.toAssignTask) {
      this.taskService.assignTaskToUser(user.userId, this.toAssignTask.id);
    }
    this.closeAssignPopin();
  }

  openAssignPopin (event: Event, task: Task) {
    event.stopPropagation();
    this.toAssignTask = task;
    this.showAssignPopin = true;
  }

  isUserNotMember (project: Project, user: User): boolean {
    return !project.members?.some(member => member.id.userId === user.userId);
  }

  onDeleteProject (id: number) {
    console.log("deleting project with id: " + id);
    this.projectService.deleteProject(id);
  }

  onShowTasks () {
    this.showTasks = !this.showTasks;
  }
  onInviteUsers (event: Event, projectId: number) {
    event.stopPropagation();
    this.showInvitePopin = true;
  }

  closeInvitePopin () {
    this.showInvitePopin = false;
  }

  closeAssignPopin () {
    this.showAssignPopin = false;
    this.toAssignTask = undefined;
    if (this.project) {
      this.taskService.getTasks();
    }
  }

  closeTasks () {
    this.showTasks = false;
  }

  closeTaskForm () {
    this.showAddTaskForm = false;
    if (this.project) {
      this.taskService.getTasks();
    }
  }

  closeMembersPopin () {
    this.showMembersPopin = false;
  }

  ngOnInit (): void {
    if (this.project) {
      this.taskService.getTasks();
    }
  }


}

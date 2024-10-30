import { Component, Input, OnInit } from '@angular/core';
import { Project, ProjectService } from '../../service/project.service';
import { Router } from '@angular/router';
import { User } from '../../service/auth.service';
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
  @Input() tasks: Task[] = [];

  showInvitePopin: boolean = false;
  showTaskPopin: boolean = false;
  showAssignPopin: boolean = false;
  showMembersPopin: boolean = false;
  showTasks: boolean = false;
  showAddTaskForm: boolean = false;
  
  loggedInUserId = parseInt(localStorage.getItem('loggedInUserId') ?? "0");

  taskName: string = '';
  taskDescription: string = '';
  taskPriority: string = 'MEDIUM';
  taskStatus: string = '';
  taskDueDate: Date = new Date();
  toAssignTask?: Task;
  taskPopinId: number = 0;


  constructor(private projectService: ProjectService,
    private taskService: TaskService,
    private router: Router) {
    console.log(this.router.url);
  }

  onTaskChange (task: Task) {
    task.modified = true;
  }

  updateTask(updatedTask: Task, project: Project) {
    this.taskService.updateTask(updatedTask, project.id, this.loggedInUserId);
    updatedTask.modified = false;
  }

  hasChanges (editedTask: Task) {
    //find task by id in tasks$
    const task = this.taskService.tasksSubject.value.find(task => task.id === editedTask.id);
    return (editedTask.name !== task?.name ||
      editedTask.description !== task?.description ||
      editedTask.priority !== task?.priority ||
      editedTask.status !== task?.status ||
      editedTask.dueDate !== task?.dueDate);
  }

  getUserName (user_id: number | undefined) {
    return this.users.find(user => user.userId === user_id)?.userName;
  }

  onShowTaskPopin (task: Task) {
    this.showTaskPopin = true;
    this.taskPopinId = task.id;
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
      projectId,
      this.loggedInUserId
    );

    formTask.reset();
    this.taskPriority = 'MEDIUM';
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
      this.taskService.assignTaskToUser(user.userId, this.toAssignTask.id, this.loggedInUserId);
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

  closeTaskPopin () {
    this.showTaskPopin = false;
    
  this.taskPopinId = 0;
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

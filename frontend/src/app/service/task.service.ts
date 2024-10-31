import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApiService } from './api.service';
import { ToastService } from './toast.service';
import { User } from './auth.service';

export interface Task {
  id: number;
  name: string;
  description: string;
  priority: string;
  status: string;
  dueDate: Date;
  projectId?: number;
  assignedTo?: number;
  modified?: boolean;
}

export interface TaskMember {
  taskId: number;
  userId: number;
}

export interface TaskRequest {
  task: Task;
  projectId: number;
  userId: number;
}

export interface TaskHistoryItem {
  id: number,
  task: Task,
  modifiedBy: number,
  modificationType: string,
  oldValue: string,
  newValue: string,
  dateModified: Date
}

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  tasksSubject = new BehaviorSubject<Task[]>([]);
  tasks$ = this.tasksSubject.asObservable();
  taskHistoryItemsSubject = new BehaviorSubject<TaskHistoryItem[]>([]);
  taskHistoryItems$ = this.taskHistoryItemsSubject.asObservable();
  prioritiesSubject = new BehaviorSubject<string[]>([]);
  priorities$ = this.prioritiesSubject.asObservable();
  statusesSubject = new BehaviorSubject<string[]>([]);
  statuses$ = this.statusesSubject.asObservable();

  constructor(
    private toastService: ToastService,
    private apiService: ApiService) { }

  updateTask (updatedTask: Task, project_id: number, user_id:number) {
    this.apiService.updateTask(updatedTask, project_id, user_id).subscribe({
      next: () => {
        this.getTasks();
        this.toastService.addToast('Task updated !');
      },
      error: () => {
        this.toastService.addToast('Task not updated !');
      },
    });
  }

  createTask (name: string, description: string, priority: string, status: string, dueDate: Date, projectId: number, user_id:number) {
    this.apiService.createTask(name, description, priority, status, dueDate, projectId, user_id).subscribe({
      next: (res) => {
        console.log('res createTask: ' + res);

        try {
          this.getTasks();
          this.toastService.addToast('Task added !');
        } catch (error) {
          this.toastService.addToast(
            'Task not created !'
          );
        }
      },
      error: (error) => {
        this.toastService.addToast('Task not created !');
      },
    });
  }

  assignTaskToUser (userId: number, taskId: number, authorId: number) {
    this.apiService.assignTaskToUser(userId, taskId, authorId).subscribe({
      next: () => {
        this.getTasks();
        this.toastService.addToast('Task assigned !');
      },
      error: () => {
        this.toastService.addToast(
          'Task not assigned !');
      },
    });
  }

  getTasks () {
    this.apiService.getTasks().subscribe({
      next: (res) => {
        console.log('res getTasks: ' + res);
        try {
          this.tasksSubject.next(res);
          this.toastService.addToast('Tasks fetched !');
        } catch (error) {
          this.toastService.addToast('Tasks not fetched !');
        }
      },
      error: (error) => {
        this.toastService.addToast('Tasks not fetched !');
      },
    });
  }

  getTaskHistoryItems () {
    this.apiService.getTaskHistoryItems().subscribe({
      next: (res) => {
        console.log('res getTaskHistoryItems: ' + res);
        try {
          this.taskHistoryItemsSubject.next(res);
          this.toastService.addToast('Tasks History fetched !');
        } catch (error) {
          this.toastService.addToast('Tasks History not fetched !');
        }
      },
      error: (error) => {
        this.toastService.addToast('Tasks History not fetched !');
      },
    });
  }



  getPriorities () {
    this.apiService.getPriorities().subscribe({
      next: (res) => {
        console.log('res priorities: ' + res);
        try {
          this.prioritiesSubject.next(res);
          this.toastService.addToast('Priorities fetched !');
        } catch (error) {
          this.toastService.addToast('Priorities not fetched !');
        }
      },
      error: (error) => {
        this.toastService.addToast('Priorities not fetched !');
      },
    });
  }

  getStatuses () {
    this.apiService.getStatuses().subscribe({
      next: (res) => {
        console.log('res statuses : ' + res);
        try {
          this.statusesSubject.next(res);
          this.toastService.addToast('Statuses fetched !');
        } catch (error) {
          this.toastService.addToast('Statuses not fetched !');
        }
      },
      error: (error) => {
        this.toastService.addToast('Statuses not fetched !');
      },
    });
  }
}
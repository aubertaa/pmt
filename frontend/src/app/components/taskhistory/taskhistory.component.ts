import { Component, Input } from '@angular/core';
import { Task, TaskHistoryItem } from '../../service/task.service';
import { User } from '../../service/auth.service';

@Component({
  selector: 'app-taskhistory',
  templateUrl: './taskhistory.component.html',
  styleUrl: './taskhistory.component.css'
})
export class TaskhistoryComponent {
  @Input() taskHistoryItems: TaskHistoryItem[] = [];
  @Input() tasks: Task[] = [];
  @Input() users: User[] = [];

  getUserName (modified_by: number) {
    return this.users.find(user => user.userId === modified_by)?.userName;
  }
  
}

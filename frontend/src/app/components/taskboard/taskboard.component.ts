import { Component, Input } from '@angular/core';
import { Task } from '../../service/task.service';
import { User } from '../../service/auth.service';

@Component({
  selector: 'app-taskboard',
  templateUrl: './taskboard.component.html',
  styleUrl: './taskboard.component.css'
})
export class TaskboardComponent {

  @Input() tasks: Task[] = [];
  @Input() statuses: string[] = [];
  @Input() users: User[] = [];
  
  getUserName (user_id: number | undefined) {
    return this.users.find(user => user.userId === user_id)?.userName;
  }

}

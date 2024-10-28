import { Component, Input } from '@angular/core';
import { Task } from '../../service/task.service';

@Component({
  selector: 'app-taskboard',
  templateUrl: './taskboard.component.html',
  styleUrl: './taskboard.component.css'
})
export class TaskboardComponent {

  @Input() tasks: Task[] = [];
  @Input() statuses: string[] = [];
  
}

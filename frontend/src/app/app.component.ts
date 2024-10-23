import { Component } from '@angular/core';
import { ToastService } from './service/toast.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})

export class AppComponent {
  title = 'PMT';

  constructor(public toastService: ToastService) {}

  removeToast(index: number) {
    this.toastService.remove(index);
  }
}


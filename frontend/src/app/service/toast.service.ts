import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  toasts: string[] = [];

  constructor() {}

  addToast(message: string) {
    this.toasts.push(message);
  }

  remove(index: number) {
    this.toasts.splice(index, 1);
  }
}

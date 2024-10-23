import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-user-input-form',
  templateUrl: './user-input-form.component.html',
  styleUrl: './user-input-form.component.css',
})
export class UserInputFormComponent implements OnInit {
  @Input() isTryingToLogin?: boolean;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    console.log('isTryingToLogin: ' + this.isTryingToLogin);
  }

  onSubmit(form: NgForm) {
    //valider tous les champs
    form.form.markAllAsTouched();
    
    if (form.invalid) {
      return;
    }

    console.log(form);
    if (this.isTryingToLogin) {
      console.log('connection');
      this.authService.login(form.value.username, form.value.password);
      
    } else {
      console.log('inscription');
      this.authService.register(form.value.username, form.value.email, form.value.password);
    }

    form.reset();
  }
}

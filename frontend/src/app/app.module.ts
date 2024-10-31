import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserInputFormComponent } from './components/user-input-form/user-input-form.component';
import { provideHttpClient } from '@angular/common/http';
import { ProjectItemComponent } from './projects/project-item/project-item.component';
import { TaskboardComponent } from './components/taskboard/taskboard.component';
import { TaskhistoryComponent } from './components/taskhistory/taskhistory.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    UserInputFormComponent,
    ProjectItemComponent,
    TaskboardComponent,
    TaskhistoryComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent],
})
export class AppModule {}

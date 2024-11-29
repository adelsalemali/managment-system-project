import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProjectListComponent } from './list-project/list-project.component';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';
import { CreateProjectComponent } from './create-project/create-project.component'; 
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UpdateProjectComponent } from './update-project/update-project.component';
import { DetailsProjectComponent } from './details-project/details-project.component';
import { CreateTaskComponent } from './create-task/create-task.component';
import { UpdateTaskComponent } from './update-task/update-task.component';
import { ListUserComponent } from './list-user/list-user.component';
import { UpdateUserComponent } from './update-user/update-user.component';

@NgModule({
  declarations: [ 
    AppComponent, 
    ProjectListComponent,
    CreateProjectComponent,
    UpdateProjectComponent,
    DetailsProjectComponent,
    CreateTaskComponent,
    UpdateTaskComponent,
    ListUserComponent,
    UpdateUserComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule, 
    ReactiveFormsModule
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withFetch()) 
  ],
  bootstrap: [AppComponent]
})
export class AppModule { } 

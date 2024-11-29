import { createComponent, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProjectListComponent } from './list-project/list-project.component';
import { CreateProjectComponent } from './create-project/create-project.component';
import { UpdateProjectComponent } from './update-project/update-project.component';
import { DetailsProjectComponent } from './details-project/details-project.component';
import { UpdateTaskComponent } from './update-task/update-task.component';
import { CreateTaskComponent } from './create-task/create-task.component';
import { ListUserComponent } from './list-user/list-user.component';
import { UpdateUserComponent } from './update-user/update-user.component';

const routes: Routes = [
  { path: '', redirectTo: 'users', pathMatch: 'full' },  
  { path: 'users', component: ListUserComponent },
  { path: 'update-user/:id', component: UpdateUserComponent},
  { path: 'details-user/:userId', component: ProjectListComponent },    
  { path: 'create-project/:id', component: CreateProjectComponent }, 
  { path: 'update-project/:projectId', component: UpdateProjectComponent },
  { path: 'details-user/:projectId', component: ProjectListComponent }, 
  { path: 'details-project/:id', component: DetailsProjectComponent },
  { path: 'update-task/:id', component: UpdateTaskComponent },
  { path: 'create-task/:id', component: CreateTaskComponent }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
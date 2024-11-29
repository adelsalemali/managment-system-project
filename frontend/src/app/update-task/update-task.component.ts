import { Component, OnInit } from '@angular/core';
import { TaskService } from '../services-task/task.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Task } from '../model/task';

@Component({
  selector: 'app-update-task',
  templateUrl: './update-task.component.html',
  styleUrls: ['./update-task.component.css']
}) 
export class UpdateTaskComponent implements OnInit {

  id: number = 0;
  task: Task = new Task();
  projectId: number = 0;

  constructor(private taskService: TaskService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.projectId = this.route.snapshot.queryParams['projectId'];
    this.taskService.getTaskById(this.projectId, this.id).subscribe(
      data => {
        this.task = data;
      },
      error => console.log(error)
    );
  }

  onSubmit() {
    this.task.projectId = this.projectId;
  
    console.log('Submitting task:', this.task);
    this.taskService.updateTask(this.task).subscribe(() => {
      this.router.navigate(['/details-project/', this.projectId]);
    }, error => console.log(error));
  }
}

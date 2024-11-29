import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Task } from '../model/task';
import { TaskService } from '../services-task/task.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.css']
})
export class CreateTaskComponent implements OnInit {

  taskForm!: FormGroup;
  projectId!: number;

  constructor(
    private taskService: TaskService, 
    private route: ActivatedRoute, 
    private router: Router, 
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.projectId = +this.route.snapshot.params['id'];
    console.log('Project ID:', this.projectId);

    this.taskForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.maxLength(255)]],
      dueDate: ['', Validators.required],
      status: ['', Validators.required],
    });
  }

  get name() {
    return this.taskForm.get('name');
  }

  get description() {
    return this.taskForm.get('description');
  }

  get dueDate() {
    return this.taskForm.get('dueDate');
  }

  get status() {
    return this.taskForm.get('status');
  }

  saveTask() {
    if (this.taskForm.invalid) {
      return;
    }
   
    const task: Task = {
      ...this.taskForm.value,
      projectId: this.projectId
    };

    this.taskService.createTask(this.projectId, task).subscribe(
      data => {
        console.log("Task created successfully:", data);
        
        this.router.navigate(['/details-project', this.projectId]);
      },
      error => console.log(error)
    );
  }

  onSubmit() {
    console.log('Submitting task:', this.taskForm.value);
    this.saveTask();
  }
}

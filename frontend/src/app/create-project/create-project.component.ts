import { Component, OnInit } from '@angular/core';
import { Project } from '../model/project';
import { ProjectService } from '../service-project/project.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.css']
})
export class CreateProjectComponent implements OnInit {

  projectForm!: FormGroup;
  userId!: number;

  constructor(
    private projectService: ProjectService, 
    private route: ActivatedRoute, 
    private router: Router, 
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.userId = +this.route.snapshot.params['id'];
    console.log('User ID:', this.userId);

    this.projectForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.maxLength(200)]],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      status: ['', [Validators.required, Validators.maxLength(25)]]
    });
  }

  get name() { return this.projectForm.get('name'); }
  get description() { return this.projectForm.get('description'); }
  get startDate() { return this.projectForm.get('startDate'); }
  get endDate() { return this.projectForm.get('endDate'); }
  get status() { return this.projectForm.get('status'); }

  saveProject() {
    if (this.projectForm.invalid) {
      return;
    }

    const project: Project = {
      ...this.projectForm.value,
      userId: this.userId
    };

    this.projectService.createProjectById(this.userId, project).subscribe(
      data => {
        console.log("Project created successfully:", data);
        this.router.navigate(['/details-user', this.userId]);
      },
      error => console.error("Error creating project:", error)
    );
  }

  onSubmit() {
    console.log('Submitting project:', this.projectForm.value);
    this.saveProject();
  }
}

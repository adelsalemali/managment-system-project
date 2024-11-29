import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../service-project/project.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from '../model/project';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-project.component.html',
  styleUrls: ['./update-project.component.css']
})
export class UpdateProjectComponent implements OnInit {

  id!: number;
  userId!: number;
  project: Project = new Project();

  constructor(private projectService: ProjectService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    
    this.id = +this.route.snapshot.params['projectId'];
    this.userId = +this.route.snapshot.queryParams['userId'];
    
    if (this.userId && this.id) {
      
      this.projectService.getProjectById(this.userId, this.id).subscribe(
        data => {
          this.project = data;
        },
        error => console.log('Error fetching project data:', error)
      );
    } else {
      console.error("Invalid userId or projectId.");
    }
  }

  onSubmit() {
    this.projectService.updateProjectById(this.project).subscribe(
      () => this.router.navigate(['/details-user', this.userId]),
      error => console.log('Error updating project:', error)
    );
  }

  goToProjectList() {
    this.router.navigate(['/projects']);
  }
}

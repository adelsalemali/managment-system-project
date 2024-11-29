import { Component, OnInit } from '@angular/core';
import { Project } from '../model/project';
import { ProjectService } from '../service-project/project.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PagbleList } from '../page/pagble-list';

@Component({ 
  selector: 'app-employee-list',
  templateUrl: './list-Project.component.html',
  styleUrls: ['./list-Project.component.css']
})
export class ProjectListComponent implements OnInit {

  id!: number;
  project!: Project[];
  pages!: PagbleList<Project>;
  page: number = 0;
  size: number = 10;
  totalPages: number = 0;
  sortBy: string = 'id';
  status: string = '';
  name: string = '';
  description: string = '';
  sortDirection: string = 'ASC';

  constructor(
    private projectService: ProjectService, 
    private router: Router, 
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.id = +params.get('userId')!;
      if (!this.id) {
        console.error("User ID is undefined or invalid.");
        return;
      }
      this.loadProjects();
    });
  }
  

  detailsProject(id: number) {
    this.router.navigate(['details-project', id]);
  } 

  loadProjects(): void {
    this.projectService.findAll(this.id, this.page, this.size, this.sortBy, this.sortDirection)
      .subscribe(data => {
        console.log(data);
        this.pages = data;
        this.project = data.content || [];
        this.totalPages = data.totalPages;
      }, error => {
        console.log('Error fetching projects:', error);
        this.project = [];
      });
  }  

  updateProject(projectId: number): void {
    this.router.navigate(['update-project', projectId], { queryParams: { userId: this.id } }); 
  }

  deleteProject(id: number) {
    
    if (window.confirm('Are you sure you want to delete this project?')) {
      this.projectService.deleteProjectId(this.id, id).subscribe(data => {
        console.log(data);
        this.loadProjects();   
      }, error => {
        console.error('Error deleting project:', error);
      });
    } else {
      console.log('Deletion canceled');
    }
  }

  performSearch(): void {
    const searchCriteria = {
        name: this.name.trim(),
        description: this.description.trim(),
        status: this.status.trim(),
    };

    this.projectService.searchProjects(
      this.id,
      searchCriteria.name,
      searchCriteria.description,
      searchCriteria.status,
      this.page,
      this.size,
      this.sortBy,
      this.sortDirection,
      
    ).subscribe(
      data => {
        console.log('Search Results:', data);
        this.pages = data;
        this.project = data.content || [];
        this.totalPages = data.totalPages;
      },
      error => {
        console.error('Error searching projects:', error);
        this.project = [];
      }
    );
}


  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadProjects();
    }
  }

  previousPage() {
    if (this.page > 0) {  
      this.page--;
      this.loadProjects();
    }
  }
}
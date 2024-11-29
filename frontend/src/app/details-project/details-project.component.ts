import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from '../services-task/task.service';
import { Task } from '../model/task';
import { PagbleList } from '../page/pagble-list';

@Component({
  selector: 'app-details-project',
  templateUrl: './details-project.component.html',
  styleUrls: ['./details-project.component.css']
})
export class DetailsProjectComponent implements OnInit {

  id: number = 0;
  status: string = '';
  name: string = '';
  description: string = '';
  tasks: Task[] = [];
  page!: PagbleList<Task>;
  pageNumber: number = 0;
  pageSize: number = 5 ;
  sortBy: string = 'id';
  sortDirection: string = 'ASC';
  totalPages: number = 0;

  constructor(private route: ActivatedRoute, private taskService: TaskService, private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getTasks();
  }

  getTasks(): void {
    this.taskService.getTasksByProjectId(this.id, this.pageNumber, this.pageSize, this.sortBy, this.sortDirection)
      .subscribe(data => {
        this.page = data;
        this.tasks = data.content;
        this.totalPages = data.totalPages;
      }, error => {
        console.log('Error fetching tasks:', error);
        this.tasks = [];
      });
  }

  updateTask(taskId: number): void {
    this.router.navigate(['update-task', taskId], { queryParams: { projectId: this.id } });    
  }

  deleteTask(task: Task): void {
    console.log('Submitting task for deletion:', task); 
    this.taskService.deleteTask(this.id, task.id).subscribe(
      () => {
        console.log('Task deleted successfully');
        this.getTasks(); 
      },
      error => console.log('Error deleting task:', error)
    );
  }

  performSearch(): void {
    const searchCriteria = {
      name: this.name.trim(),
      description: this.description.trim(),
      status: this.status.trim(),
    };
  
    this.taskService.searchTasks(
      this.id,
      searchCriteria.name,
      searchCriteria.description,
      searchCriteria.status,
      this.pageNumber,
      this.pageSize,
      this.sortBy,
      this.sortDirection
    ).subscribe(
      data => {
        console.log('Search Results:', data);
        this.page = data;
        this.tasks = data.content || [];
        this.totalPages = data.totalPages;
      },
      error => {
        console.error('Error searching tasks:', error);
        this.tasks = [];
      }
    );
  }
  
  
  nextPage() {
    if (this.pageNumber < this.totalPages - 1) {
      this.pageNumber ++;
      this.getTasks();
    }
  }

  previousPage() {
    if (this.pageNumber > 0) {  
      this.pageNumber --;
      this.getTasks();
    }
  }
}

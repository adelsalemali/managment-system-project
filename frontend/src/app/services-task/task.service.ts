import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Task } from '../model/task';
import { PagbleList } from '../page/pagble-list';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private baseURL = "http://localhost:8080/api/v1";

  constructor(private httpClient: HttpClient) { }

   getTasksByProjectId(id: number, pageNumber: number, pageSize: number, sortBy: string, sortDirection: string): Observable<PagbleList<Task>> {
    let params = new HttpParams()
      .set('pageNumber', pageNumber.toString())
      .set('pageSize', pageSize.toString())
      .set('sortBy', sortBy)
      .set('sortDirection', sortDirection);

    return this.httpClient.get<PagbleList<Task>>(`${this.baseURL}/tasks?projectId=${id}`, { params });
  }

  getTaskById(projectId: number, taskId: number): Observable<Task> {
    return this.httpClient.get<Task>(`${this.baseURL}/task/${taskId}/${projectId}`);
  }

  createTask(projectId: number, task: Task): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}/task`, task); 
  }

  updateTask(task: Task): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/task`, task);
  }

  deleteTask(projectId: number, taskId: number): Observable<Object> {
    const tasksDto = { id: taskId, projectId };
    return this.httpClient.delete(`${this.baseURL}/task`, { body: tasksDto });
  }  

  searchTasks(
    projectId: number,
    name: string,
    description: string,
    status: string,
    page: number,
    size: number,
    sortBy: string,
    sortDirection: string
  ): Observable<PagbleList<Task>> {
    const params = new HttpParams()
      .set('pageNumber', page.toString())
      .set('pageSize', size.toString())
      .set('sortBy', sortBy)
      .set('sortDirection', sortDirection)
      .set('name', name.trim())
      .set('description', description.trim())
      .set('status', status.trim());
  
    return this.httpClient.get<PagbleList<Task>>(
      `${this.baseURL}/task/search/?projectId=${projectId}`,
      { params }
    );
  }
}
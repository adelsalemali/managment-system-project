import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project } from '../model/project';
import { PagbleList } from '../page/pagble-list';

@Injectable({ 
  providedIn: 'root'
})
export class ProjectService {

  private baseURL = "http://localhost:8080/api/projects";

  constructor(private httpClient: HttpClient) { }

  getProjectsList(): Observable<Project[]> {
    return this.httpClient.get<Project[]>(this.baseURL);
  }

  createProject(project: Project): Observable<Object> { 
    return this.httpClient.post(`${this.baseURL}`, project);
  }

  createProjectById(userId: number, project: Project): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, project);
  }

  getProjectById(userId: number, projectId: number): Observable<Project> {
    return this.httpClient.get<Project>(`${this.baseURL}/${projectId}/${userId}`);
  }

  updateProjectById(project: Project): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}`, project);
  }

  deleteProjectId(userId: number, projectId: number): Observable<Object> {
    const tasksDto = { id: projectId, userId }; 
    return this.httpClient.delete(`${this.baseURL}`, { body: tasksDto });
  }  

  findAll(userId: number, page: number, size: number, sortBy: string, sortDirection: string): Observable<PagbleList<Project>> {
    const params = new HttpParams()
      .set('pageNumber', page.toString())
      .set('pageSize', size.toString())
      .set('sortBy', sortBy)
      .set('sortDirection', sortDirection);
  
    return this.httpClient.get<PagbleList<Project>>(`${this.baseURL}/?userId=${userId}`, { params });
  }

  searchProjects(
    userId: number,
    name: string,
    description: string,
    status: string,
    page: number,
    size: number,
    sortBy: string,
    sortDirection: string
): Observable<PagbleList<Project>> {
    const params = new HttpParams()
        .set('pageNumber', page.toString())
        .set('pageSize', size.toString())
        .set('sortBy', sortBy)
        .set('sortDirection', sortDirection)
        .set('name', name.trim())
        .set('description', description.trim())
        .set('status', status.trim());

    return this.httpClient.get<PagbleList<Project>>(`${this.baseURL}/search/?userId=${userId}`, { params });
  }
}
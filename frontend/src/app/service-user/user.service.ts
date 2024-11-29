import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PagbleList } from '../page/pagble-list';
import { User } from '../model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/api/user'; 

  constructor(private httpClient: HttpClient) { }

  findAll(page: number, size: number, sortBy: string, sortDirection: string): Observable<PagbleList<User>> {
    const params = new HttpParams()
      .set('pageNumber', page.toString())
      .set('pageSize', size.toString())
      .set('sortBy', sortBy)
      .set('sortDirection', sortDirection);
  
    return this.httpClient.get<PagbleList<User>>(`${this.baseUrl}s`, { params });
  }

  createUser(user: User): Observable<Object> { 
    return this.httpClient.post(`${this.baseUrl}`, user);
  }

  getUser(id: number): Observable<User> {
    return this.httpClient.get<User>(`${this.baseUrl}/${id}`);
  }

  updateUser(id: number, user: User): Observable<Object> {
    return this.httpClient.put(`${this.baseUrl}/${id}`, user);
  }

  deleteUser(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }

  getusersList(): Observable<User[]> {
    return this.httpClient.get<User[]>(this.baseUrl);
  }

  searchProjects(
    userName: string,
    firstName: string,
    lastName: string,
    page: number,
    size: number,
    sortBy: string,
    sortDirection: string
): Observable<PagbleList<User>> {
    const params = new HttpParams()
        .set('pageNumber', page.toString())
        .set('pageSize', size.toString())
        .set('sortBy', sortBy)
        .set('sortDirection', sortDirection)
        .set('userName', userName.trim())
        .set('firstName', firstName.trim())
        .set('lastName', lastName.trim());

    return this.httpClient.get<PagbleList<User>>(`${this.baseUrl}/search`, { params });
  }
}

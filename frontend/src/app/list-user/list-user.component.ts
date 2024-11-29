import { Component } from '@angular/core';
import { UserService } from '../service-user/user.service';
import { User } from '../model/user';
import { Router } from '@angular/router';
import { PagbleList } from '../page/pagble-list';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrl: './list-user.component.css'
})
export class ListUserComponent {

  userName: string = '';
  firstName: string = '';
  lastName: string = '';
  users: User[] = [];
  pages!: PagbleList<User>;
  page: number = 0;
  size: number = 5;
  totalPages: number = 0;
  sortBy: string = 'id';
  sortDirection: string = 'ASC';

  constructor(private userService: UserService, private router: Router) {} 

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.findAll(this.page, this.size, this.sortBy, this.sortDirection).subscribe((data) => {
      this.users = data.content;
      this.totalPages = data.totalPages;
    });
  }

  detailsUsers(userId: number) {
    this.router.navigate(['details-user', userId]);
  }

  updateUser(id: number) {
    this.router.navigate(['update-user', id]);
  }

  deleteUser(id: number) {
    
    const confirmed = window.confirm("Are you sure you want to delete this user?");
    if (confirmed) {
      this.userService.deleteUser(id).subscribe(() => {
        this.loadUsers();
      });
    }
  }
  

  performSearch(): void {
    const searchCriteria = {
      userName: this.userName.trim(),
      firstName: this.firstName.trim(),
      lastName: this.lastName.trim()
    };

    this.userService.searchProjects(
      searchCriteria.userName,
      searchCriteria.firstName,
      searchCriteria.lastName,
      this.page,
      this.size,
      this.sortBy,
      this.sortDirection
    ).subscribe(
      data => {
        console.log('Search Results:', data);
        this.pages = data;
        this.users = data.content || [];
        this.totalPages = data.totalPages;
      },
      error => {
        console.error('Error searching projects:', error);
        this.users = [];
      }
    );
}

  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadUsers();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.loadUsers();
    }
  }
}

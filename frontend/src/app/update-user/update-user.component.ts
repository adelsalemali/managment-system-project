import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service-user/user.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrl: './update-user.component.css'
})
export class UpdateUserComponent implements OnInit {

  id: number = 0;
  user: User = new User();

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.userService.getUser(this.id).subscribe(data => {
      this.user = data;
    }, error => console.log(error));
  }

  onSubmit() {
    
    this.userService.updateUser(this.id, this.user).subscribe(data => {
      this.goToUserList();
    }, error => console.log(error));
  }

  goToUserList() {
    this.router.navigate(['/users']);
  } 
}

import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { UserService } from '../service/user.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-adduser',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, MatInputModule, MatSelectModule, MatCardModule, MatButtonModule],
  templateUrl: './adduser.component.html',
  styleUrl: './adduser.component.scss'
})

export class AdduserComponent {
  userForm : FormGroup;
  _userService = inject(UserService);

  constructor(private fb: FormBuilder, private route : Router) {

    this.userForm = this.fb.group({
      userName: ['', Validators.required],
      password: ['', [Validators.required]],
      emailId: ['', [Validators.required, Validators.email]],
      role: ['', Validators.required],
    });

  }

  addUser() {
    if (this.userForm.valid) {
      this._userService.userName =  this.userForm.get('userName')?.value;
      this._userService.password = this.userForm.get('password')?.value;
      this._userService.email = this.userForm.get('emailId')?.value;
      this._userService.role =  this.userForm.get('role')?.value;
      this._userService.addUser();
      this.routeToUserSearch();
    }
  }

  routeToUserSearch(){
    this.route.navigate(['/usersearch']);
  }

}

import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { UserService } from '../service/user.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { User } from '../dto/user.dto';
import { RoutingService } from '../../test-module/services/routingservice/routing.service';

@Component({
  selector: 'app-edit-user',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, MatInputModule, MatSelectModule, MatCardModule, MatButtonModule],
  templateUrl: './edit-user.component.html',
  styleUrl: './edit-user.component.scss'
})
export class EditUserComponent {
  userForm : FormGroup;
  _userService = inject(UserService);
  _routingService = inject(RoutingService);

  constructor(private fb: FormBuilder, private route : Router) {

    this.userForm = this.fb.group({
      userName: [{value:'', disabled: true}, Validators.required],
      password: ['', [Validators.required]],
      emailId: ['', [Validators.required, Validators.email]],
      role: ['', Validators.required],
    });

    this.userForm.get('userName')?.setValue(this._userService.userToBeEdited.userName);
  }

  editUser() {this._userService.editUser();}

}

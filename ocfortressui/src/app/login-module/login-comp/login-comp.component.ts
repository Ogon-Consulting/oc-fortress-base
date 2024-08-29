import { response } from 'express';
import { AfterViewInit, Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterOutlet } from '@angular/router';
import { RouterModule, Routes } from '@angular/router';
import { TestServiceService } from '../../test-module/services/testservice/test-service.service';
import { LoginService } from '../login.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-login-comp',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, MatGridListModule, MatCardModule, MatInputModule, MatButtonModule, MatFormFieldModule,
    MatSidenavModule, RouterOutlet, RouterModule,
    CommonModule, MatToolbarModule, MatIconModule],
  templateUrl: './login-comp.component.html',
  styleUrls: ['./login-comp.component.scss']
})

export class LoginCompComponent {
  userId: string = '';
  passCd: string = '';
  showLoginMessage: boolean = false;
  _productList = inject(TestServiceService);
  loginFailureMessage = '';
  _loginService = inject(LoginService);
  userVerifyApiUrl: string = 'http://localhost:6080/api/v1/verifyuser';

  loginForm = new FormGroup({
    userName: new FormControl<string>('', [Validators.required]),
    password: new FormControl<string>('', [Validators.required]),
  });

  constructor(private router: Router, private http: HttpClient) { }

  async verifyUser() {
    let userId = this.loginForm.controls.userName.value || '';
    let password = this.loginForm.controls.password.value || '';
    let params = new HttpParams();
    params = params.append('userId', userId);
    params = params.append('password', password);
    const response = await this._loginService.verifyUser(userId, password);
    if ((response == 'Success')) {
      this._productList.loginUserId = userId;
      this._loginService.loggedUserName = userId;
      this.router.navigate(['/home']);
    } else {
      this.loginFailureMessage = 'Invalid credentials! Please try again...';
      this.showLoginMessage = true;
    }
  }
}

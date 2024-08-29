import { HttpClient } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { RouterModule, Routes } from '@angular/router';
import { LoginService } from './login.service';

@NgModule({
  declarations: [],
  providers:[HttpClient, LoginService],
  imports: [
    CommonModule, MatFormFieldModule, BrowserModule, ReactiveFormsModule, FormArray, FormBuilder, FormControl, FormGroup, FormsModule, Validators, BrowserAnimationsModule, MatGridListModule,
    MatCardModule, MatInputModule, MatButtonModule, RouterModule
  ]
})
export class LoginModuleModule { }


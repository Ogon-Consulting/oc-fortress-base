import { Products } from './../../test-module/services/testservice/test-service.service';
import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { DialogCompComponent } from '../../test-module/dialog-comp/dialog-comp.component';
import { firstValueFrom } from 'rxjs';
import { LoginService } from '../../login-module/login.service';
import { User } from '../dto/user.dto';
import { RoutingService } from '../../test-module/services/routingservice/routing.service';

const editUser = {
  userName: '',
  password: '',
  emailId: '',
  role: '',
  addedDate:'',
  updatedDate:''
};

@Injectable({
  providedIn: 'root'
})


export class UserService {
  _loginService = inject(LoginService);
  _routingService = inject(RoutingService);
  userName: string = '';
  password: string = '';
  email: string = '';
  role: string = '';
  alertBoxMessage = '';
  #availableUsersList = signal<User[]>([]);
  addUserAPIURL = 'http://localhost:6080/api/v1/addUser';
  editUserAPIURL = 'http://localhost:6080/api/v1/updateUser';
  deletetUserAPIURL = 'http://localhost:6080/api/v1/deleteUser';
  getAllUsersAPIURL = 'http://localhost:6080/api/v1/getAllUsers';
  updateUserPasswordAPIURL = 'http://localhost:6080/api/v1/updateUserPassword';
  deleteUserAPIURL = 'http://localhost:6080/api/v1/deleteUser';
  userLookupAPIURL = 'http://localhost:6080/api/v1/userLookup';
  userData!: User;
  #searchUserName = signal<string>('');
  #searchOption = signal<string>('');
  #searchText = signal<string>('');
  #searchRole = signal<string>('');
  roles: string[] = ['Admin','Producer'];
  #userToBeEdited = signal<User>(editUser);

  constructor(private http:HttpClient, private router : Router, public dialog: MatDialog) { }

  set userToBeEdited(userToBeEdited:User){
    this.#userToBeEdited.set(userToBeEdited);
  }

  get userToBeEdited(): User{
    return this.#userToBeEdited();
  }

  set searchUserName(searchUserName:string){
    this.#searchUserName.set(searchUserName);
  }

  get searchUserName():string{
    return this.#searchUserName();
  }

  set searchOption(searchOption:string){
    this.#searchOption.set(searchOption);
  }

  get searchOption():string{
    return this.#searchOption();
  }

  set searchText(searchText:string){
    this.#searchText.set(searchText);
  }

  get searchText():string{
    return this.#searchText();
  }

  set searchRole(searchRole:string){
    this.#searchRole.set(searchRole);
  }

  get searchRole():string{
    return this.#searchRole();
  }

  set availableUsersList(availableUsersList : User[]){
    this.#availableUsersList.set(availableUsersList);
  }

  get availableUsersList() : User[]{
    return this.#availableUsersList();
  }

  async searchUser(){

    let params = new HttpParams();
    params = params.append('searchUserName',this.searchUserName);
    params = params.append('searchOption',this.searchOption);
    params = params.append('searchText',this.searchText);
    params = params.append('searchRole',this.searchRole);

    await firstValueFrom(this.http.get<any>(this.userLookupAPIURL, { params })).then((data)=>{
      this.userData = data.user;
      this.#availableUsersList.set(data.user);
      console.log(this.userData);
      if('Failed' === data.status){
        this.alertBoxMessage = data.status;
        this.openAlertDialog();
      }
    })
  }

  async addUser(){
    this.alertBoxMessage = '';
    const hashedPassword = this._loginService.hashPassword(this.password);
    const ipdatas = {
      userName: this.userName,
      password: this.password,
      emailId: this.email,
      role: this.role,
      addedDate:'',
      updatedDate:''
    };
    await firstValueFrom(this.http.post<any>(this.addUserAPIURL, ipdatas)).then((data)=>{
      this.alertBoxMessage = data.status;
      this.openAlertDialog();
      this.listAllUsers();
    })
  }

  async listAllUsers(){
    this.alertBoxMessage = '';
    await firstValueFrom(this.http.get<any>(this.getAllUsersAPIURL)).then((data)=>{
      this.#availableUsersList.set(data.users)
      if('Failed' === data.status){
        this.alertBoxMessage = data.status;
        this.openAlertDialog();
      }
    })
  }


  async updateUserPassword(password : string){
    this.alertBoxMessage = '';
    const hashedPassword = this._loginService.hashPassword(password);
    console.log('hashedPassword',hashedPassword);
    const ipdatas = {
      userName: this.userName,
      password: hashedPassword,
      emailId: '',
      role: '',
      addedDate:'',
      updatedDate:''
    };
    await firstValueFrom(this.http.post<any>(this.updateUserPasswordAPIURL, ipdatas)).then((data)=>{
      console.log(data);
      this.alertBoxMessage = data.status;
      this.openAlertDialog();
    })
  }

  async deleteUser(user : User){
    this.alertBoxMessage = '';
    let params = new HttpParams();
    params = params.append('userName',user.userName);
    await firstValueFrom(this.http.get<any>(this.deleteUserAPIURL, { params })).then((data)=>{
      console.log(data);
      this.alertBoxMessage = data.status;
      this.openAlertDialog();
      this.listAllUsers();
    })
  }

  openAlertDialog() {
    const dialogRef = this.dialog.open(DialogCompComponent, {
      data: {
        message: this.alertBoxMessage,
        buttonText: {
          cancel: 'Done'
        }
      }, enterAnimationDuration: '500ms',
      exitAnimationDuration: '400ms',
    });
  }

  async editUser(){
    this.alertBoxMessage = '';
    //const hashedPassword = this._loginService.hashPassword(this.password);
    const ipdatas = {
      userName: this.userToBeEdited.userName,
      password: this.userToBeEdited.password,
      emailId: this.userToBeEdited.emailId,
      role: this.userToBeEdited.role,
      addedDate:'',
      updatedDate:''
    };
    console.log('User to be edited',ipdatas)
    await firstValueFrom(this.http.post<any>(this.editUserAPIURL, ipdatas)).then((data)=>{
      this.alertBoxMessage = data.status;
      this.openAlertDialog();
      this.listAllUsers();
      this._routingService.routeToUserManagement();
    })
  }
}

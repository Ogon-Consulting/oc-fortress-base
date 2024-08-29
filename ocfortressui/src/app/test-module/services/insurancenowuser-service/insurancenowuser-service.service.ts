import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { InsuranceNowUser } from '../../dto/insurancenowusers.dto';
import { MatSelectChange } from '@angular/material/select';
import { InsuranceNowUsersComponent } from '../../insurance-now-users/insurance-now-users.component';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { TestServiceService } from '../testservice/test-service.service';
import { BundleService } from '../bundleservice/bundle.service';

@Injectable({
  providedIn: 'root'
})
export class InsurancenowuserServiceService {

  #insuranceNowUsersList = signal<InsuranceNowUser[]>([]);
  getAllInsuranceUsersAPIURL = 'http://localhost:6080/api/v1/getInsuranceNowUsers';
 // #executionUser = signal<string>('');
  #executionUserLoginId = signal<string>('');
  #executionUserPassword = signal<string>('');
  #executionUserRole = signal<string>('');
  #roles = signal<string[]>([]);
  #filteredUsers = signal<InsuranceNowUser[]>([]);
  usersRoleMap = new Map();
  #isBundleChosen = signal<boolean>(false);
  _testService = inject(TestServiceService);
  _bundleService = inject(BundleService);
  constructor(private http: HttpClient, private router: Router, public dialog: MatDialog) {

  }

  set isBundleChosen(isBundleChosen: boolean) {
    this.#isBundleChosen.set(isBundleChosen);
  }

  get isBundleChosen(): boolean {
    return this.#isBundleChosen();
  }

  set roles(roles: string[]) {
    this.#roles.set(roles);
  }

  get roles(): string[] {
    return this.#roles();
  }

  // set executionUser(executionUser: string) {
  //   this.#executionUser.set(executionUser);
  // }

  // get executionUser(): string {
  //   return this.#executionUser();
  // }

  set executionUserLoginId(executionUserLoginId: string) {
    this.#executionUserLoginId.set(executionUserLoginId);
  }

  get executionUserLoginId(): string {
    return this.#executionUserLoginId();
  }

  set executionUserPassword(executionUserPassword: string) {
    this.#executionUserPassword.set(executionUserPassword);
  }

  get executionUserPassword(): string {
    return this.#executionUserPassword();
  }

  set executionUserRole(executionUserRole: string) {
    this.#executionUserRole.set(executionUserRole);
  }

  get executionUserRole(): string {
    return this.#executionUserRole();
  }


  set insuranceNowUsersList(insuranceNowUsersList: InsuranceNowUser[]) {
    this.#insuranceNowUsersList.set(insuranceNowUsersList);
  }

  get insuranceNowUsersList(): InsuranceNowUser[] {
    return this.#insuranceNowUsersList();
  }

  set filteredUsers(filteredUsers: InsuranceNowUser[]) {
    this.#filteredUsers.set(filteredUsers);
  }

  get filteredUsers(): InsuranceNowUser[] {
    return this.#filteredUsers();
  }

  async getAllInsuranceNowUsers(){
    await firstValueFrom(this.http.get<any>(this.getAllInsuranceUsersAPIURL)).then((data)=>{
      console.log(data.inowusers);
      this.#insuranceNowUsersList.set(data.inowusers);
      this.#roles.set([...new Set(this.insuranceNowUsersList.map(user => user.userTypeCd))]);
    }).catch((error)=>{
      console.error(error);
    })
  }

  onRoleChange(event: MatSelectChange): void {
    console.log('event', event);
    console.log('onrole change', event.value);
    this.filteredUsers = [];
    this.filteredUsers = this.insuranceNowUsersList.filter(user => user.userTypeCd === event.value);
  }

  initiateExecution(){
    if(this.#isBundleChosen()){
      this._bundleService.executionUserRole = this.#executionUserRole();
      this._bundleService.executionUserLoginId = this.#executionUserLoginId();
      this._bundleService.executionUserPassword = this.#executionUserPassword();
      this._bundleService.executeBundles();
    }else{
      this._testService.executionUserRole = this.#executionUserRole();
      this._testService.executionUserLoginId = this.#executionUserLoginId();
      this._testService.executionUserPassword = this.#executionUserPassword();
      this._testService.getNextJobID();
    }
    this.#executionUserRole.set('');
    this.#executionUserLoginId.set('');
    this.#executionUserPassword.set('');
  }
}

import { Component, inject } from '@angular/core';
import { UserService } from '../service/user.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule, Sort } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Router } from '@angular/router';
import { RoutingService } from '../../test-module/services/routingservice/routing.service';
import { TestServiceService } from '../../test-module/services/testservice/test-service.service';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { User } from '../dto/user.dto';

@Component({
  selector: 'app-usersearch',
  standalone: true,
  imports: [MatInputModule, FormsModule, ReactiveFormsModule,MatCardModule, MatButtonModule,MatSelectModule,
    MatPaginator, MatPaginatorModule,MatSort, MatSortModule, MatTableModule, MatSort, MatSidenavModule, MatIconModule, MatProgressSpinnerModule, MatSlideToggleModule],
  templateUrl: './usersearch.component.html',
  styleUrl: './usersearch.component.scss'
})

export class UsersearchComponent {
  userForm!: FormGroup;
  searchOptions = ['Contains','Starts With'];
  searchOption = '';
  _userService = inject(UserService);
  _routingService = inject(RoutingService);
  _testService = inject(TestServiceService);
  displayedColumns = ['actions','userName','role','emailId','addedDate','updatedDate'];

  constructor(private formBuilder: FormBuilder, private _liveAnnouncer: LiveAnnouncer, private router:Router){
    this.resetValues();
  }

  searchUser(){
    this._userService.searchUserName = this.userForm.get('searchUserName')?.value;
    this._userService.searchOption = this.userForm.get('searchOption')?.value;
    this._userService.searchText = this.userForm.get('searchText')?.value;
    this._userService.searchRole = this.userForm.get('searchRole')?.value;
    this._userService.searchUser();
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  routeToAddUser(){
    this.router.navigate(['/adduser']);
  }

  resetValues(){
    this.userForm = this.formBuilder.group({
      searchUserName: [''],
      searchOption:[''],
      searchText:[''],
      searchRole:['']
    });
  }

  editUser(index:number){
    console.log('in edit user');
    const selectedUser = this._userService.availableUsersList[index];
    console.log('selectedUser',selectedUser);
    this._userService.userToBeEdited = selectedUser;
    this._routingService.routeToEditUser();
  }

  deleteUser(index:number){
    const selectedUser = this._userService.availableUsersList[index];
    this._userService.deleteUser(selectedUser);
  }
}

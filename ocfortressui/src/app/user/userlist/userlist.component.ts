import { Component, inject } from '@angular/core';
import { UserService } from '../service/user.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule, Sort } from '@angular/material/sort';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-userlist',
  standalone: true,
  imports: [MatTableModule, MatPaginator, MatPaginatorModule,MatSort, MatSortModule, MatCardModule],
  templateUrl: './userlist.component.html',
  styleUrl: './userlist.component.scss'
})
export class UserlistComponent {

  _userService = inject(UserService);
  displayedColumns = ['userName','password','role','emailId','addedDate','updatedDate'];
  constructor(private _liveAnnouncer: LiveAnnouncer){
    this._userService.listAllUsers();
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }
}

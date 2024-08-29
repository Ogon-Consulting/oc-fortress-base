import { CommonModule } from '@angular/common';
import { Component, inject, ViewChild } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxChange, MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NgxPaginationModule } from 'ngx-pagination';
import { TestServiceService } from '../services/testservice/test-service.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { Bundles } from '../dto/bundle.dto';
import { BundleService } from '../services/bundleservice/bundle.service';
import { SchedulerserviceService } from '../services/schedulerservice/schedulerservice.service';
import { JobschedulerComponent } from '../jobscheduler/jobscheduler.component';
import { RoutingService } from '../services/routingservice/routing.service';
import { InsuranceNowUsersComponent } from '../insurance-now-users/insurance-now-users.component';

@Component({
  selector: 'app-bundlelist',
  standalone: true,
  imports: [MatIconModule, MatSort, MatSortModule, MatProgressSpinnerModule, MatButtonModule, MatCheckboxModule, MatPaginator, MatPaginatorModule, NgxPaginationModule, MatTooltipModule, MatSidenavModule, CommonModule, MatTableModule, FormsModule, ReactiveFormsModule, MatFormFieldModule,],
  templateUrl: './bundlelist.component.html',
  styleUrl: './bundlelist.component.scss'
})
export class BundlelistComponent {
  _testService = inject(TestServiceService);
  _bundleService = inject(BundleService);
  _schedulerService = inject(SchedulerserviceService);
  _routingService = inject(RoutingService);
  displayedColumns: string[] = ['bundleChecked', 'actions', 'bundleName', 'description', 'testCases', 'statusCd', 'recentJobId', 'lastExecutionResult', 'lastExecutedBy', 'lastExecutedOn'];
  clickedRows = new Set<Bundles>();
  scheduledList = new Set<Bundles>();
  dataSource: MatTableDataSource<Bundles> = new MatTableDataSource();

  constructor(private router: Router, public dialog: MatDialog) {
    this.getAllActiveBundles();
    this.clickedRows.clear();
    this.scheduledList.clear();
  }

  @ViewChild(MatSort)
  sort!: MatSort;

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
  getAllActiveBundles() {
    this._bundleService.getAllActiveBundles();
  }

  scheduleBundle(): void {
    const selectedBundlesList: string[] = [];
    const clickedRowsArray: Bundles[] = Array.from(this.clickedRows);
    for (const bundle of clickedRowsArray) {
      selectedBundlesList.push(bundle.bundleId.toString());
    }
    this._schedulerService.itemsToBeScheduled = selectedBundlesList;
    this._schedulerService.scheduleCategory = 'Bundles';
    this.clickedRows.clear();
    const dialogRef = this.dialog.open(JobschedulerComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '400ms',
    });
  }

  createBundlesListToExecute(index: number): void {
    const bundleId = this._bundleService.bundlesList[index].bundleId;
    if (this._bundleService.bundlesToBeExecuted.includes(bundleId)) {
      const index = this._bundleService.bundlesToBeExecuted.indexOf(bundleId);
      if (index !== -1) {
        this._bundleService.bundlesToBeExecuted.splice(index, 1);
      }
    } else {
      this._bundleService.bundlesToBeExecuted.push(bundleId);
    }
  }

  async unScheduleSelectedJob(jobName: string) {
    this._schedulerService.unScheduleJob(jobName);
  }

  routeToBundleList() {
    this.router.navigate(['/bundles']);
  }

  updateCheckedRow(index: number, event: MatCheckboxChange): void {
    let bundle: Bundles;
    bundle = this._bundleService.bundlesList[index];
    if (!event.checked) {
      this.clickedRows.delete(bundle);
      bundle.bundleSelected = false;
    } else {
      this.clickedRows.add(bundle);
      bundle.bundleSelected = true;
    }
  }

  executeBundle(): void {
    this._bundleService.bundlesToBeExecuted = [];
    this.clickedRows.forEach(element => {
      this._bundleService.bundlesToBeExecuted.push(element.bundleId);
    });
    this.openInsuranceNowUserDialog();
    //this._bundleService.executeBundles();
    this.initializeSets();
    this.getAllActiveBundles();
  }

  initializeSets(): void {
    this.clickedRows.clear();
    this.scheduledList.clear();
  }

  openInsuranceNowUserDialog() {
    const dialogRef = this.dialog.open(InsuranceNowUsersComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '400ms',
    });
  }

  addToScheduleList(index: number): void {
    const bundle = this._bundleService.bundlesList[index]
    if (!this.scheduledList.has(bundle)) {
      this.scheduledList.add(bundle);
    }
  }

  deleteBundle(index: number): void {
    this._bundleService.deleteBundleById(this._bundleService.bundlesList[index].bundleId);
  }
}

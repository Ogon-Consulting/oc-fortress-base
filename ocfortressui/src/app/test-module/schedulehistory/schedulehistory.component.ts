import { Component, inject, ViewChild } from '@angular/core';
import { TestServiceService } from '../services/testservice/test-service.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatCheckboxChange, MatCheckboxModule } from '@angular/material/checkbox';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NgxPaginationModule } from 'ngx-pagination';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSidenavModule } from '@angular/material/sidenav';
import { DialogCompComponent } from '../dialog-comp/dialog-comp.component';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { SchedulerserviceService } from '../services/schedulerservice/schedulerservice.service';
import { LoginService } from '../../login-module/login.service';
import { MatSlideToggle, MatSlideToggleChange, MatSlideToggleModule } from '@angular/material/slide-toggle';
import { RoutingService } from '../services/routingservice/routing.service';

export interface ScheduleHistory {
  jobSelected: boolean;
  jobName: string;
  description: string;
  jobType: string;
  prevTriggerTime: string;
  nextTriggerTime: string;
  status: string;
  scheduledAt: string;
}
@Component({
  selector: 'app-schedulehistory',
  standalone: true,
  imports: [MatIconModule, MatSort, MatSortModule, MatProgressSpinnerModule, MatButtonModule, MatCheckboxModule, MatPaginator, MatPaginatorModule,
     MatTooltipModule, MatSidenavModule, CommonModule, MatTableModule, FormsModule, ReactiveFormsModule, MatFormFieldModule, MatSlideToggleModule],
  templateUrl: './schedulehistory.component.html',
  styleUrl: './schedulehistory.component.scss'
})

export class SchedulehistoryComponent {
  _testService = inject(TestServiceService);
  _schedulerService = inject(SchedulerserviceService);
  _routingService = inject(RoutingService);
  //dataSource: MatTableDataSource<ScheduleHistory> = new MatTableDataSource();
  clickedRows = new Set<ScheduleHistory>();
  displayedColumns: string[] = ['actions','jobName', 'description', 'category','jobType', 'scheduledAt', 'prevTriggerTime', 'nextTriggerTime', 'status'];
  alertBoxMessage = '';
  slideToggleState = false;

  constructor(private router: Router, public dialog: MatDialog){
    this.getAllActiveSchedules();
  }

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this._schedulerService.dataSource.paginator = this.paginator;
  }

  getAllSchedules(){
    this._schedulerService.fetchAllScheduledJobs();
  }
  getAllActiveSchedules(){
    this._schedulerService.fetchAllActiveScheduledJobs();
    this.slideToggleState = false;
  }

  updateCheckedRow(index: number, event: MatCheckboxChange) {
    let jobHistory: ScheduleHistory;
    jobHistory = this._schedulerService.scheduleHistories[index];
    if (!event.checked) {
      this.clickedRows.delete(jobHistory);
      jobHistory.jobSelected = false;
    } else {
      this.clickedRows.add(jobHistory);
      jobHistory.jobSelected = true;
    }
  }

  unScheduleJob(index: number): void {
    const selectedJobName = this._schedulerService.scheduleHistories[index].jobName;
    this._schedulerService.unScheduleJob(selectedJobName);
    if(this.slideToggleState){
      this._schedulerService.fetchAllScheduledJobs();
    }else{
      this._schedulerService.fetchAllActiveScheduledJobs();
    }
    this.routeToScheduleHistories();
  }

  routeToScheduleHistories() {
    this._routingService.routeToScheduleHistories();
  }

  async refreshProducts() {
    this._testService.refreshCarriersStatesProducts();
    this.alertBoxMessage = 'Job Schedules Refreshed';
    this.openAlertDialog();
  }

  openAlertDialog() {
    const dialogRef = this.dialog.open(DialogCompComponent, {
      data: {
        message: this.alertBoxMessage,
      }, enterAnimationDuration: '500ms',
      exitAnimationDuration: '400ms',
    });
  }

  onToggleChange(event: MatSlideToggleChange){
    if (event.checked) {
      this.getAllSchedules();
    } else {
      this.getAllActiveSchedules();
    }
  }
}

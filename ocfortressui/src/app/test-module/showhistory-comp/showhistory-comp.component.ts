import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule, DatePipe } from '@angular/common';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'
import { MatGridListModule } from '@angular/material/grid-list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatExpansionModule } from '@angular/material/expansion';
import { Component, inject, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { NavigationEnd, Router } from '@angular/router';
import { TestCompComponent } from '../test-comp/test-comp.component';
import { HttpClient } from '@angular/common/http';
import { TestServiceService } from '../services/testservice/test-service.service';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NgxPaginationModule } from 'ngx-pagination';
import { MatTooltip } from '@angular/material/tooltip';
import { RoutingService } from '../services/routingservice/routing.service';

export interface TestCaseHistory {
  testCaseName: string;
  executedOn: string;
  testStatus: string;
  referenceNumber: string;
  transactionNumber: string;
  executedBy: string;
  product: string;
  testResult: string;
  jobId: string;
}

@Component({
  selector: 'app-showhistory-comp',
  standalone: true,
  imports: [MatCardModule, MatSelectModule, CommonModule, MatDatepickerModule, MatNativeDateModule,
    MatInputModule, MatIconModule, MatButtonModule, MatProgressSpinnerModule, NgxPaginationModule,
    MatGridListModule, MatFormFieldModule, MatCheckboxModule, MatToolbarModule, MatSidenavModule, TestCompComponent, NgxSpinnerModule, MatTooltip,
    CommonModule, MatTableModule, DatePipe, MatPaginator, MatPaginatorModule, MatProgressSpinnerModule, MatButtonModule, MatExpansionModule],
  templateUrl: './showhistory-comp.component.html',
  styleUrl: './showhistory-comp.component.scss'
})

export class ShowhistoryCompComponent {
  listHistoryApiUrl = 'http://localhost:6080/api/v1/gethistory';
  displayedColumns: string[] = ['product', 'testCaseName', 'executedOn', 'testStatus', 'referenceNumber', 'executedBy', 'jobId', 'testResult'];
  testHistoryMap: Map<string, TestCaseHistory[]> = new Map();
  testMapEntries: { key: string, values: TestCaseHistory[] }[] = [];
  dataSource: MatTableDataSource<TestCaseHistory> = new MatTableDataSource();
  dataValue = '';
  dataValue1 = '';
  _productList = inject(TestServiceService);
  _routingService = inject(RoutingService);

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  constructor(private router: Router, private http: HttpClient, private spinner: NgxSpinnerService) {
    this.getHistory();
  }

  async getHistory() {
    this._productList.spinnerText = 'Loading Histories...';
    this.showSpinner();
    const [success, error] = await this._productList.loadHistories();
    if (success) {

    } else if (error) {

    }
    this.hideSpinner();
  }

  getMapEntries(): [string, TestCaseHistory[]][] {
    return Array.from(this.testHistoryMap.entries());
  }

  expandedPanels: Set<string> = new Set();

  togglePanel(entry: { key: string }): void {
    if (this.isPanelExpanded(entry)) {
      this.expandedPanels.delete(entry.key);
    } else {
      this.expandedPanels.add(entry.key);
    }
  }

  isPanelExpanded(entry: { key: string }): boolean {
    return this.expandedPanels.has(entry.key);
  }

  showSpinner() {
    this._productList.showSpinner = true;
  }

  hideSpinner() {
    this._productList.showSpinner = false;
  }
}

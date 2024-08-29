import { JoblogService } from './../services/joblog/joblog.service';
import { Component, inject, ViewChild } from '@angular/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'
import { TestServiceService } from '../services/testservice/test-service.service';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { firstValueFrom } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { MatSort, Sort, MatSortModule } from '@angular/material/sort';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatTooltip } from '@angular/material/tooltip';
import { RoutingService } from '../services/routingservice/routing.service';
export interface JobLog {
  jobId: string;
  initiatedOn: string;
  jobStatus: string;
  initiatedBy: string;
  product: string;
  outputMessage: string;
  completedOn: string;
}

@Component({
  selector: 'app-joblog-comp',
  standalone: true,
  imports: [MatProgressSpinnerModule, MatIconModule, MatSidenavModule, MatExpansionModule, MatPaginator, MatPaginatorModule, MatTableModule, MatButtonModule,
    MatSort, MatSortModule, MatTooltip
  ],
  templateUrl: './joblog-comp.component.html',
  styleUrl: './joblog-comp.component.scss'
})
export class JoblogCompComponent {
  _testService = inject(TestServiceService);
  _joblogService = inject(JoblogService);
  _routingService = inject(RoutingService);
  expandedPanels: Set<string> = new Set();
  displayedColumns: string[] = ['jobId', 'product', 'jobStatus', 'initiatedOn', 'completedOn', 'initiatedBy'];

  constructor(private router: Router, private http: HttpClient, private _liveAnnouncer: LiveAnnouncer) {
    this.getJobLog();
  }

  @ViewChild(MatSort)
  sort!: MatSort;

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this._joblogService.dataSource.paginator = this.paginator;
    this._joblogService.dataSource.sort = this.sort;
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  routeToHistory() {
    this.router.navigate(['/history']);
  }

  routeToJobLog() {
    this.router.navigate(['/joblog']);
  }

  goToHome() {
    this.router.navigate(['/home']);
  }

  goTologin() {
    this.router.navigate(['/login']);
  }

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

  async getJobLog() {
    this._joblogService.getJobLog();
  }
}

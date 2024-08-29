import { inject, Injectable } from '@angular/core';
import { TestServiceService } from '../testservice/test-service.service';
import { firstValueFrom } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { JobLog } from '../../dto/joblog.dto';

@Injectable({
  providedIn: 'root'
})
export class JoblogService {
  _testService = inject(TestServiceService);
  dataSource: MatTableDataSource<JobLog> = new MatTableDataSource();
  fetchJobLogUrl = 'http://localhost:6080/api/v1/getjoblog';

  constructor(private http: HttpClient, private router: Router) { }
  async getJobLog() {
    this._testService.spinnerText = 'Fetching Job History...';
    this._testService.showSpinner = true;
    this.showSpinner();
    let params = new HttpParams();
    params = params.append('carrierId',this._testService.selectedCarrierId);
    params = params.append('stateCd',this._testService.selectedstateCd);
    params = params.append('product',this._testService.selectedProduct);

    let result = await firstValueFrom(this.http.get<any>(this.fetchJobLogUrl, { params })).then((data) => {
      this.dataSource.data = data.joblog;
    }).catch(() => {
      this.hideSpinner();
    })
      .finally(() => {
        this.hideSpinner();
      });
    return result;
  }
  showSpinner() {
    this._testService.showSpinner = true;
  }

  hideSpinner() {
    this._testService.showSpinner = false;
  }
}

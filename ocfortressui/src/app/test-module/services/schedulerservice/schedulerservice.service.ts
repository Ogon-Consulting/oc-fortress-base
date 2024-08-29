import { inject, Injectable, signal } from '@angular/core';
import { DialogCompComponent } from '../../dialog-comp/dialog-comp.component';
import { TestServiceService } from '../testservice/test-service.service';
import { firstValueFrom } from 'rxjs';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BundleService } from '../bundleservice/bundle.service';
import { sign } from 'crypto';
import { LoginService } from '../../../login-module/login.service';
import { MatTableDataSource } from '@angular/material/table';
import { InsurancenowuserServiceService } from '../insurancenowuser-service/insurancenowuser-service.service';
export interface ScheduleHistory {
  jobSelected: boolean;
  jobName: string;
  description: string;
  category: string;
  jobType: string;
  prevTriggerTime: string;
  nextTriggerTime: string;
  status: string;
  scheduledAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class SchedulerserviceService {
  scheduleJobAPIUrl = 'http://localhost:6080/api/v1/schedule';
  unscheduleJobAPIUrl = 'http://localhost:6080/api/v1/unschedule';
  getAllActiveSchedulesJobAPIUrl = 'http://localhost:6080/api/v1/getAllActiveSchedule';
  getAllActiveJobAPIUrl = 'http://localhost:6080/api/v1/getAllSchedule';
  _testService = inject(TestServiceService);
  _bundleService = inject(BundleService);
  _loginService = inject(LoginService);
  _insuranceNowUserService = inject(InsurancenowuserServiceService);
  #scheduleCategory = signal<string>('');
  #itemsToBeScheduled = signal<string[]>([]);
  #scheduleHistories = signal<ScheduleHistory[]>([]);
  alertBoxMessage = '';
  addtl_message = '';
  #scheduledDateTime = signal<string>('');
  #selectedDate = signal<string>('');
  #selectedTime = signal<string>('');
  #dayOfTheWeek = signal<string>('');
  #month = signal<string>('');
  #dayOfTheMonth = signal<string>('');
  #hour = signal<string>('');
  #minute = signal<string>('');
  #second = signal<string>('');
  dataSource: MatTableDataSource<ScheduleHistory> = new MatTableDataSource();

  constructor(private http: HttpClient, public dialog: MatDialog, private router: Router) { }

  set itemsToBeScheduled(itemsToBeScheduled: string[]) {
    this.#itemsToBeScheduled.set(itemsToBeScheduled);
  }

  get itemsToBeScheduled(): string[] {
    return this.#itemsToBeScheduled();
  }

  set scheduleHistories(scheduleHistories: ScheduleHistory[]) {
    this.#scheduleHistories.set(scheduleHistories);
  }

  get scheduleHistories(): ScheduleHistory[] {
    return this.#scheduleHistories();
  }

  set scheduleCategory(scheduleCategory: string) {
    this.#scheduleCategory.set(scheduleCategory);
  }

  get scheduleCategory(): string {
    return this.#scheduleCategory();
  }

  set scheduledDateTime(scheduledDateTime: string) {
    this.#scheduledDateTime.set(scheduledDateTime);
  }

  get scheduledDateTime(): string {
    return this.#scheduledDateTime();
  }

  set selectedDate(selectedDate: string) {
    this.#selectedDate.set(selectedDate);
  }

  get selectedDate(): string {
    return this.#selectedDate();
  }

  set selectedTime(selectedTime: string) {
    this.#selectedTime.set(selectedTime);
  }

  get selectedTime(): string {
    return this.#selectedTime();
  }

  set dayOfTheWeek(dayOfTheWeek: string) {
    this.#dayOfTheWeek.set(dayOfTheWeek);
  }

  get dayOfTheWeek(): string {
    return this.#dayOfTheWeek();
  }

  set month(month: string) {
    this.#month.set(month);
  }

  get month(): string {
    return this.#month();
  }

  set dayOfTheMonth(dayOfTheMonth: string) {
    this.#dayOfTheMonth.set(dayOfTheMonth);
  }

  get dayOfTheMonth(): string {
    return this.#dayOfTheMonth();
  }

  set hour(hour: string) {
    this.#hour.set(hour);
  }

  get hour(): string {
    return this.#hour();
  }

  set minute(minute: string) {
    this.#minute.set(minute);
  }

  get minute(): string {
    return this.#minute();
  }

  set second(second: string) {
    this.#second.set(second);
  }

  get second(): string {
    return this.#second();
  }

  async scheduleItems() {
    if (this.itemsToBeScheduled.length == 0) {
      this.alertBoxMessage = "Please select items to schedule";
      this.openAlertDialog();
      return;
    }
    const date = this.selectedDate;
    const time = this.selectedTime;
    this.scheduledDateTime = '';
    let isCronscheduler = 'No';
    let cronschedulerConfig = '';
    if (date !== '' && date != null) {
      this.scheduledDateTime = `${this.formatDate(new Date(date))}T${this.formatTime(time)}`;
    } else {
      cronschedulerConfig = ((this.second === null || this.second === '') ? '0' : this.second) + ' ' + ((this.minute === null || this.minute === '') ? '*' : this.minute) + ' ' + ((this.hour === null || this.hour === '') ? '*' : this.hour) + ' ' + ((this.dayOfTheMonth === null || this.dayOfTheMonth === '') ? '?' : this.dayOfTheMonth) + ' ' + ((this.month === null || this.month === '') ? '*' : this.month) + ' ' + ((this.dayOfTheMonth != null && this.dayOfTheMonth != '') ? '?' : (this.dayOfTheWeek === null || this.dayOfTheWeek === '') ? '*' : this.dayOfTheWeek);
      isCronscheduler = 'Yes';
    }
    const ipdatas = {
      carrierId: this._testService.selectedCarrierId,
      stateCd: this._testService.selectedstateCd,
      lob: this._testService.selectedProduct,
      category: this.scheduleCategory,
      scheduleDate: this.scheduledDateTime,
      zoneTime: Intl.DateTimeFormat().resolvedOptions().timeZone,
      scheduleItemsList: this.itemsToBeScheduled,
      executedBy: this._loginService.loggedUserName,
      isCronscheduler: isCronscheduler,
      cronschedulerConfig: cronschedulerConfig,
      loginUserId: this._insuranceNowUserService.executionUserLoginId,
      loginPassword: this._insuranceNowUserService.executionUserPassword
    };
    const response = await firstValueFrom(this.http.post<any>(this.scheduleJobAPIUrl, ipdatas)).then((data) => {
      this.alertBoxMessage = data.message;
      this.openAlertDialog()
      if (this.scheduleCategory == 'Bundles') {
        this._bundleService.getAllActiveBundles();
      } else {
        this._testService.fetchTestCases();
      }
      this.#itemsToBeScheduled.set([]);
    }).catch((error) => {
      console.error(error);
    });
  }

  openAlertDialog() {
    const dialogRef = this.dialog.open(DialogCompComponent, {
      data: {
        message: this.alertBoxMessage,
        addtl_message: this.addtl_message,
        testid: '',
        buttonText: {
          cancel: 'Done'
        }
      }, enterAnimationDuration: '500ms',
      exitAnimationDuration: '400ms',
    });
    if (this.addtl_message == '') {
      setTimeout(() => {
        dialogRef.close();
      }, 3500);
    }
  }

  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }

  formatTime(time: string): string {
    return time.length === 5 ? `${time}:00` : time;
  }

  async unScheduleJob(jobName: string) {
    let params = new HttpParams();
    params = params.append('jobName', jobName);
    await firstValueFrom(this.http.get<any>(this.unscheduleJobAPIUrl, { params })).then((data) => {
      this.alertBoxMessage = data.message;
      this.openAlertDialog();
      this.fetchAllActiveScheduledJobs();
    }).catch((e) => { console.error(e) });
  }

  async fetchAllActiveScheduledJobs() {
    await firstValueFrom(this.http.get<any>(this.getAllActiveSchedulesJobAPIUrl))
      .then((data) => {
        this.#scheduleHistories.set(data.scheduledjobs);
        this.dataSource = data.scheduledjobs;
      });
  }

  async fetchAllScheduledJobs() {
    await firstValueFrom(this.http.get<any>(this.getAllActiveJobAPIUrl))
      .then((data) => {
        this.#scheduleHistories.set(data.scheduledjobs);
        this.dataSource = data.scheduledjobs;
      });
  }
}

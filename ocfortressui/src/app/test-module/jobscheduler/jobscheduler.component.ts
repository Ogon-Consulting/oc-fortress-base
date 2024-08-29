import { MatSelectModule } from '@angular/material/select';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogRef, MatDialogContent, MatDialogActions, MatDialogContainer, MatDialogModule, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { DateAdapter, MAT_DATE_FORMATS, MAT_NATIVE_DATE_FORMATS, MatNativeDateModule, NativeDateAdapter } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DialogCompComponent } from '../dialog-comp/dialog-comp.component';
import { SchedulerserviceService } from '../services/schedulerservice/schedulerservice.service';
import { InsurancenowuserServiceService } from '../services/insurancenowuser-service/insurancenowuser-service.service';

@Component({
  selector: 'app-jobscheduler',
  standalone: true,
  imports: [MatDialogContent, MatDialogActions, MatDialogContainer, MatDialogModule, MatButtonModule, MatFormFieldModule,
    MatInputModule, FormsModule, MatDatepickerModule, MatNativeDateModule, ReactiveFormsModule, MatSelectModule],
  providers: [{ provide: DateAdapter, useClass: NativeDateAdapter }, { provide: MAT_DATE_FORMATS, useValue: MAT_NATIVE_DATE_FORMATS },],
  templateUrl: './jobscheduler.component.html',
  styleUrl: './jobscheduler.component.scss'
})
export class JobschedulerComponent {
  alertBoxMessage = '';
  _schedulerService = inject(SchedulerserviceService);
  _insuranceNowUserService = inject(InsurancenowuserServiceService);
  selectedDate: FormControl = new FormControl('');
  selectedTime: FormControl = new FormControl('');
  dayOfTheWeek: FormControl = new FormControl('');
  month: FormControl = new FormControl('');
  dayOfTheMonth: FormControl = new FormControl('');
  hour: FormControl = new FormControl('');
  minute: FormControl = new FormControl('');
  second: FormControl = new FormControl(0);
  daysOfWeek = [
    { value: '', viewValue: '' },
    { value: '?', viewValue: 'Any Day' },
    { value: 1, viewValue: 'Sunday' },
    { value: 2, viewValue: 'Monday' },
    { value: 3, viewValue: 'Tuesday' },
    { value: 4, viewValue: 'Wednesday' },
    { value: 5, viewValue: 'Thursday' },
    { value: 6, viewValue: 'Friday' },
    { value: 7, viewValue: 'Saturday' }
  ];
  monthsOfYear = [
    { value: '', viewValue: '' },
    { value: '*', viewValue: 'Any Month' },
    { value: 'JAN', viewValue: 'January' },
    { value: 'FEB', viewValue: 'February' },
    { value: 'MAR', viewValue: 'March' },
    { value: 'APR', viewValue: 'April' },
    { value: 'MAY', viewValue: 'May' },
    { value: 'JUN', viewValue: 'June' },
    { value: 'JUL', viewValue: 'July' },
    { value: 'AUG', viewValue: 'August' },
    { value: 'SEP', viewValue: 'September' },
    { value: 'OCT', viewValue: 'October' },
    { value: 'NOV', viewValue: 'November' },
    { value: 'DEC', viewValue: 'December' }
  ];
  daysOfMonth = [
    { value: '', viewValue: '' },
    { value: '*', viewValue: 'Any Day Of Month' },
    { value: 1, viewValue: '1st' },
    { value: 2, viewValue: '2nd' },
    { value: 3, viewValue: '3rd' },
    { value: 4, viewValue: '4th' },
    { value: 5, viewValue: '5th' },
    { value: 6, viewValue: '6th' },
    { value: 7, viewValue: '7th' },
    { value: 8, viewValue: '8th' },
    { value: 9, viewValue: '9th' },
    { value: 10, viewValue: '10th' },
    { value: 11, viewValue: '11th' },
    { value: 12, viewValue: '12th' },
    { value: 13, viewValue: '13th' },
    { value: 14, viewValue: '14th' },
    { value: 15, viewValue: '15th' },
    { value: 16, viewValue: '16th' },
    { value: 17, viewValue: '17th' },
    { value: 18, viewValue: '18th' },
    { value: 19, viewValue: '19th' },
    { value: 20, viewValue: '20th' },
    { value: 21, viewValue: '21st' },
    { value: 22, viewValue: '22nd' },
    { value: 23, viewValue: '23rd' },
    { value: 24, viewValue: '24th' },
    { value: 25, viewValue: '25th' },
    { value: 26, viewValue: '26th' },
    { value: 27, viewValue: '27th' },
    { value: 28, viewValue: '28th' },
    { value: 29, viewValue: '29th' },
    { value: 30, viewValue: '30th' },
    { value: 31, viewValue: '31st' }
  ];

  hoursOfTheDay = [
    { value: '', viewValue: '' },
    { value: 0, viewValue: '00' },
    { value: 1, viewValue: '01' },
    { value: 2, viewValue: '02' },
    { value: 3, viewValue: '03' },
    { value: 4, viewValue: '04' },
    { value: 5, viewValue: '05' },
    { value: 6, viewValue: '06' },
    { value: 7, viewValue: '07' },
    { value: 8, viewValue: '08' },
    { value: 9, viewValue: '09' },
    { value: 10, viewValue: '10' },
    { value: 11, viewValue: '11' },
    { value: 12, viewValue: '12' },
    { value: 13, viewValue: '13' },
    { value: 14, viewValue: '14' },
    { value: 15, viewValue: '15' },
    { value: 16, viewValue: '16' },
    { value: 17, viewValue: '17' },
    { value: 18, viewValue: '18' },
    { value: 19, viewValue: '19' },
    { value: 20, viewValue: '20' },
    { value: 21, viewValue: '21' },
    { value: 22, viewValue: '22' },
    { value: 23, viewValue: '23' }
  ];

  minutesOfHour = [
    { value: '', viewValue: '' },
    { value: 0, viewValue: '00' },
    { value: 1, viewValue: '01' },
    { value: 2, viewValue: '02' },
    { value: 3, viewValue: '03' },
    { value: 4, viewValue: '04' },
    { value: 5, viewValue: '05' },
    { value: 6, viewValue: '06' },
    { value: 7, viewValue: '07' },
    { value: 8, viewValue: '08' },
    { value: 9, viewValue: '09' },
    { value: 10, viewValue: '10' },
    { value: 11, viewValue: '11' },
    { value: 12, viewValue: '12' },
    { value: 13, viewValue: '13' },
    { value: 14, viewValue: '14' },
    { value: 15, viewValue: '15' },
    { value: 16, viewValue: '16' },
    { value: 17, viewValue: '17' },
    { value: 18, viewValue: '18' },
    { value: 19, viewValue: '19' },
    { value: 20, viewValue: '20' },
    { value: 21, viewValue: '21' },
    { value: 22, viewValue: '22' },
    { value: 23, viewValue: '23' },
    { value: 24, viewValue: '24' },
    { value: 25, viewValue: '25' },
    { value: 26, viewValue: '26' },
    { value: 27, viewValue: '27' },
    { value: 28, viewValue: '28' },
    { value: 29, viewValue: '29' },
    { value: 30, viewValue: '30' },
    { value: 31, viewValue: '31' },
    { value: 32, viewValue: '32' },
    { value: 33, viewValue: '33' },
    { value: 34, viewValue: '34' },
    { value: 35, viewValue: '35' },
    { value: 36, viewValue: '36' },
    { value: 37, viewValue: '37' },
    { value: 38, viewValue: '38' },
    { value: 39, viewValue: '39' },
    { value: 40, viewValue: '40' },
    { value: 41, viewValue: '41' },
    { value: 42, viewValue: '42' },
    { value: 43, viewValue: '43' },
    { value: 44, viewValue: '44' },
    { value: 45, viewValue: '45' },
    { value: 46, viewValue: '46' },
    { value: 47, viewValue: '47' },
    { value: 48, viewValue: '48' },
    { value: 49, viewValue: '49' },
    { value: 50, viewValue: '50' },
    { value: 51, viewValue: '51' },
    { value: 52, viewValue: '52' },
    { value: 53, viewValue: '53' },
    { value: 54, viewValue: '54' },
    { value: 55, viewValue: '55' },
    { value: 56, viewValue: '56' },
    { value: 57, viewValue: '57' },
    { value: 58, viewValue: '58' },
    { value: 59, viewValue: '59' }
  ];

  secondsOfMinutes = [
    { value: '', viewValue: '' },
    { value: 0, viewValue: '00' },
    { value: 1, viewValue: '01' },
    { value: 2, viewValue: '02' },
    { value: 3, viewValue: '03' },
    { value: 4, viewValue: '04' },
    { value: 5, viewValue: '05' },
    { value: 6, viewValue: '06' },
    { value: 7, viewValue: '07' },
    { value: 8, viewValue: '08' },
    { value: 9, viewValue: '09' },
    { value: 10, viewValue: '10' },
    { value: 11, viewValue: '11' },
    { value: 12, viewValue: '12' },
    { value: 13, viewValue: '13' },
    { value: 14, viewValue: '14' },
    { value: 15, viewValue: '15' },
    { value: 16, viewValue: '16' },
    { value: 17, viewValue: '17' },
    { value: 18, viewValue: '18' },
    { value: 19, viewValue: '19' },
    { value: 20, viewValue: '20' },
    { value: 21, viewValue: '21' },
    { value: 22, viewValue: '22' },
    { value: 23, viewValue: '23' },
    { value: 24, viewValue: '24' },
    { value: 25, viewValue: '25' },
    { value: 26, viewValue: '26' },
    { value: 27, viewValue: '27' },
    { value: 28, viewValue: '28' },
    { value: 29, viewValue: '29' },
    { value: 30, viewValue: '30' },
    { value: 31, viewValue: '31' },
    { value: 32, viewValue: '32' },
    { value: 33, viewValue: '33' },
    { value: 34, viewValue: '34' },
    { value: 35, viewValue: '35' },
    { value: 36, viewValue: '36' },
    { value: 37, viewValue: '37' },
    { value: 38, viewValue: '38' },
    { value: 39, viewValue: '39' },
    { value: 40, viewValue: '40' },
    { value: 41, viewValue: '41' },
    { value: 42, viewValue: '42' },
    { value: 43, viewValue: '43' },
    { value: 44, viewValue: '44' },
    { value: 45, viewValue: '45' },
    { value: 46, viewValue: '46' },
    { value: 47, viewValue: '47' },
    { value: 48, viewValue: '48' },
    { value: 49, viewValue: '49' },
    { value: 50, viewValue: '50' },
    { value: 51, viewValue: '51' },
    { value: 52, viewValue: '52' },
    { value: 53, viewValue: '53' },
    { value: 54, viewValue: '54' },
    { value: 55, viewValue: '55' },
    { value: 56, viewValue: '56' },
    { value: 57, viewValue: '57' },
    { value: 58, viewValue: '58' },
    { value: 59, viewValue: '59' }
  ];

  constructor(private dialogRef: MatDialogRef<JobschedulerComponent>, private messagedialogRef: MatDialog) {
    this._insuranceNowUserService.getAllInsuranceNowUsers();
  }

  getCurrentTime(): string {
    const now = new Date();
    const hours = ('0' + now.getHours()).slice(-2);
    const minutes = ('0' + now.getMinutes()).slice(-2);
    return `${hours}:${minutes}`;
  }

  schedule(): void {
    const validationFailed = this.validateScheduleInput();

    if (validationFailed) {
      this.openAlertDialog();
    } else {
      this._schedulerService.selectedDate = this.selectedDate.value;
      this._schedulerService.selectedTime = this.selectedTime.value;
      this._schedulerService.dayOfTheWeek = this.dayOfTheWeek.value;
      this._schedulerService.month = this.month.value;
      this._schedulerService.dayOfTheMonth = this.dayOfTheMonth.value;
      this._schedulerService.hour = this.hour.value;
      this._schedulerService.minute = this.minute.value;
      this._schedulerService.second = this.second.value;
      const response = this._schedulerService.scheduleItems();
      this.dialogRef.close(null);
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

  onCancelClick(): void {
    this.dialogRef.close(null);
  }

  validateScheduleInput(): boolean {

    let validationFailed = false;
    const isSelectedDateEmpty = this.selectedDate.value === null || this.selectedDate.value === '';
    const isSelectedTimeEmpty = this.selectedTime.value === null || this.selectedTime.value === '';
    const isDayOfTheWeekEmpty = this.dayOfTheWeek.value === null || this.dayOfTheWeek.value === '';
    const isMonthEmpty = this.month.value === null || this.month.value === '';
    const isDayOfTheMonthEmpty = this.dayOfTheMonth.value === null || this.dayOfTheMonth.value === '';
    const isHourEmpty = this.hour.value === null || this.hour.value === '';
    const isMinuteEmpty = this.minute.value === null || this.minute.value === '';
    const isSecondEmpty = this.second.value === null || this.second.value === '';

    if(!isSelectedDateEmpty && !isSelectedTimeEmpty){
      const selectedDateTime = new Date(this.selectedDate.value);
      const [hours, minutes] = this.selectedTime.value.split(':').map(Number);
      selectedDateTime.setHours(hours, minutes);
      const currentDateTime = new Date();
      if (selectedDateTime.getTime() < currentDateTime.setSeconds(0,0)) {
        this.alertBoxMessage = "Schedule Date or Time cannot be Past Date or Time";
        validationFailed = true;
      }
    }

    if (isSelectedDateEmpty && isSelectedTimeEmpty
      && isDayOfTheWeekEmpty && isMonthEmpty && isDayOfTheMonthEmpty
      && isHourEmpty) {
      this.alertBoxMessage = "Please choose at least one input to schedule";
      validationFailed = true;
    } else if (!isSelectedDateEmpty && !isSelectedTimeEmpty
      && (!isDayOfTheWeekEmpty || !isMonthEmpty || !isDayOfTheMonthEmpty
        || !isHourEmpty)) {
      this.alertBoxMessage = "Please choose either specfic Date and Time or Weekly Schedule or Monthly Schedule";
      validationFailed = true;
    } else if (!isSelectedDateEmpty && isSelectedTimeEmpty) {
      this.alertBoxMessage = "Please choose Time details for Date selected";
      validationFailed = true;
    } else if (isSelectedDateEmpty && isSelectedTimeEmpty) {
      if (!isDayOfTheWeekEmpty && (!isMonthEmpty || !isDayOfTheMonthEmpty)) {
        this.alertBoxMessage = "Please choose either Weekly Schedule or Monthly Schedule. Both cannot be entered";
        validationFailed = true;
      }
    } else if (isSelectedDateEmpty && isSelectedTimeEmpty && isDayOfTheWeekEmpty) {
      if (!isMonthEmpty && isDayOfTheMonthEmpty) {
        this.alertBoxMessage = "Day of the Month has to be selected for Monthly Schedule";
        validationFailed = true;
      }
    } else if (isSelectedDateEmpty && isSelectedTimeEmpty
      && (!isDayOfTheWeekEmpty || (!isMonthEmpty && !isDayOfTheMonthEmpty)
        && (isHourEmpty))) {
      this.alertBoxMessage = "Hours/Minutes/Seconds can be empty for Weekly Schedule or Monthly Schedule";
      validationFailed = true;
    }
    return validationFailed;
  }

  openAlertDialog() {
    const dialogRef = this.messagedialogRef.open(DialogCompComponent, {
      data: {
        message: this.alertBoxMessage,
        addtl_message: '',
        testid: '',
        buttonText: {
          cancel: 'Done'
        }
      }, enterAnimationDuration: '500ms',
      exitAnimationDuration: '400ms',
    });
  }
}

import { InsuranceNowUsersComponent } from './../insurance-now-users/insurance-now-users.component';
import { AfterViewInit, Component, inject, signal, ViewChild } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatSelectChange, MatSelectModule } from '@angular/material/select';
import { CommonModule, DatePipe } from '@angular/common';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxChange, MatCheckboxModule } from '@angular/material/checkbox';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'
import { MatGridListModule } from '@angular/material/grid-list';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSidenavModule } from '@angular/material/sidenav';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TestServiceService } from '../services/testservice/test-service.service';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { firstValueFrom } from 'rxjs/internal/firstValueFrom';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { DialogCompComponent } from '../dialog-comp/dialog-comp.component';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatTooltipModule } from '@angular/material/tooltip';
import { JobschedulerComponent } from '../jobscheduler/jobscheduler.component';
import { BundleService } from '../services/bundleservice/bundle.service';
import { BundledialogComponent } from '../bundledialog/bundledialog.component';
import { SchedulerserviceService } from '../services/schedulerservice/schedulerservice.service';
import { RoutingService } from '../services/routingservice/routing.service';
import { LoginService } from '../../login-module/login.service';
import { InsurancenowuserServiceService } from '../services/insurancenowuser-service/insurancenowuser-service.service';

export interface TestCaseData {
  testCaseSelected: boolean
  testCaseId: string;
  testCaseName: string;
  executedOn: string;
  testStatus: string;
  referenceNumber: string;
  transactionNumber: string;
  executedBy: string;
  testResult: string;
  transactionCd: string;
  jobId: string;
}

export interface JobLog {
  jobId: string;
  initiatedOn: string;
  jobStatus: string;
  initiatedBy: string;
  product: string;
  outputMessage: string;
}

@Component({
  selector: 'app-test-comp',
  standalone: true,
  imports: [MatCardModule, MatSelectModule, CommonModule, MatDatepickerModule, MatNativeDateModule, MatListModule, NgxPaginationModule,
    MatInputModule, MatIconModule, MatButtonModule, MatProgressSpinnerModule, MatPaginator, MatPaginatorModule, MatTableModule,
    MatGridListModule, FormsModule, ReactiveFormsModule, MatFormFieldModule, MatCheckboxModule, MatToolbarModule, MatSidenavModule, NgxSpinnerModule, MatTooltipModule],
  templateUrl: './test-comp.component.html',
  styleUrls: ['./test-comp.component.scss']
})

export class TestCompComponent implements AfterViewInit {
  getCarriersAPIURL = 'http://localhost:6080/api/v1/getcarriers';
  fetchTestCasesUrl = 'http://localhost:6080/api/v1/gettestcases';
  listProductApiUrl = 'http://localhost:6080/api/v1/getproducts';
  addJobLogURL = 'http://localhost:6080/api/v1/addjoblog';
  displayMessage = '';
  showProgressBar: boolean = false;
  productsList: string[] = [];
  loading: boolean = true;
  checkBoxSelected = false;
  lastSelectedState: string = '';
  selectedProduct: string = '';
  selectedState: string = '';
  fetchedTestCases: TestCaseData[] = [];
  fetchTestCasesAPIStatus: string = '';
  fetchProuctListAPISuccess = true;
  dataSource: MatTableDataSource<TestCaseData> = new MatTableDataSource();
  displayedColumns: string[] = ['testCaseCheckBox', 'actions', 'testCase', 'executedOn', 'jobId', 'status', 'executedBy', 'testResult'];
  freezePage = signal<boolean>(false);
  productsFetched = signal<boolean>(false);
  lastSelectedProduct = signal<string>('');
  _productList = inject(TestServiceService);
  _bundleService = inject(BundleService);
  _schedulerService = inject(SchedulerserviceService);
  _routingService = inject(RoutingService);
  _loginService = inject(LoginService);
  _insuranceNowUserService = inject(InsurancenowuserServiceService);
  pageNumber = 1;
  recordsPerPage = 5;
  jobLogData!: JobLog;
  inputProductSet = false;
  selectedItems!: TestCaseData;
  formInputFields = new FormGroup({
    carrierId: new FormControl<string>('', [Validators.required]),
    productSelected: new FormControl<string>('', [Validators.required]),
    stateCd: new FormControl<string>('', [Validators.required]),
  })
  fetchProductListAPIStatus: any;
  alertBoxMessage: string = '';
  alertTestID = '';
  nextJobID = '';
  addtl_message = '';

  constructor(private http: HttpClient, private formBuilder: FormBuilder, private router: Router, private spinner: NgxSpinnerService, public dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.displayMessage = '';
    this.inputProductSet = false;
    this.formInputFields.valueChanges.subscribe();
    this.getCarriers();
    this.dataSource.data = this._productList.dataSource.data;
    if (this._productList.dataSource.data.length != 0) {
      this._productList.showTestCaseList = true;
      this._productList.refreshTestCasesList = true;
    }
    this.loading = false;
    this._productList.clickedRows.clear();
    this.formInputFields.patchValue({
      carrierId: this._productList.selectedCarrierId,
      productSelected: this._productList.selectedProduct,
      stateCd: this._productList.selectedstateCd,
    });
    if (this._productList.selectedProduct != '') {
      this.inputProductSet = true;
    }
    if (this._productList.refreshTestCasesList) {
      this.fetchTestCases();
    }
  }

  openAlertDialog() {
    const dialogRef = this.dialog.open(DialogCompComponent, {
      data: {
        message: this.alertBoxMessage,
        addtl_message: this.addtl_message,
        testid: this.alertTestID,
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

  showTestExecutionSpinner() {
    this._productList.showTestExecutionSpinner = true;
  }

  hideTestExecutionSpinner() {
    this._productList.showTestExecutionSpinner = false;
  }

  showSpinner() {
    this._productList.showSpinner = true;
  }

  hideSpinner() {
    this._productList.showSpinner = false;
  }

  async fetchFieldTestDataMap(testCaseId: string) {
    this._productList.fetchFieldTestDataMap(testCaseId);
  }

  async refreshProducts() {
    this._productList.refreshCarriersStatesProducts();
    this.alertBoxMessage = 'Products Refreshed';
    this.addtl_message = '';
    this.openAlertDialog();
  }

  async getCarriers() {
    this._productList.getAllCarriers();
  }

  getCarrierStates(event: MatSelectChange) {
    if (event.value != this._productList.selectedCarrierId) {
      this._productList.selectedCarrierId = event.value;
      this._productList.selectedstateCd = '';
      this._productList.selectedProduct = '';
      this._productList.intializeArrays();
      this.formInputFields.controls.stateCd.setValue('');
      this.formInputFields.controls.productSelected.setValue('');
      this.inputProductSet = false;
      this._productList.showTestCaseList = false;
      this._productList.getCarrierStates(event.value);
    }
  }

  setProduct(event: MatSelectChange) {
    this._productList.selectedProduct = event.value;
    this.inputProductSet = true;
    this._productList.showTestCaseList = false;
    this._productList.dataSource.data = [];
  }

  getCarrierStateProducts(event: MatSelectChange) {
    if (event.value != this._productList.selectedstateCd) {
      this._productList.selectedstateCd = event.value;
      this.inputProductSet = false;
      this._productList.showTestCaseList = false;
      this._productList.dataSource.data = [];
      this._productList.carrierStateProductArray = [];
      this.formInputFields.controls.productSelected.setValue('');
      this._productList.getCarrierStateProducts(event.value);
    }
  }

  fetchTestCases() {
    this._productList.fetchTestCases();
    if ('failed' === this._productList.fetchTestCasesAPIStatus) {
      this.displayMessage = 'Test Fetch Failed!';
    } else {
      this.dataSource.data = this._productList.dataSource.data;
      this._productList.showTestCaseList = true;
      this._productList.clickedRows.clear();
      this._productList.refreshTestCasesList = false;
    }
  }

  formatDate(date: string | null) {
    if (date) {
      return new DatePipe('en-US').transform(new Date(), 'MM/dd/yyyy');
    }
    return '-';
  }

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this._productList.dataSource.paginator = this.paginator;
  }

  updateCheckedRow(index: number, event: MatCheckboxChange) {
    let testcaseData: TestCaseData;
    testcaseData = this._productList.dataSource.data[index];
    if (!event.checked) {
      this._productList.clickedRows.delete(testcaseData);
      testcaseData.testCaseSelected = false;
    } else {
      this._productList.clickedRows.add(testcaseData);
      testcaseData.testCaseSelected = true;
    }
  }

  async startTest() {
    if (this._productList.clickedRows.size === 0) {
      this.alertBoxMessage = 'Select Test Cases to execute';
      this.openAlertDialog();
      return '';
    } else {
      this.displayMessage = '';
    }
    this.openInsuranceNowUserDialog();
    return '';
  }

  openInsuranceNowUserDialog() {
    const dialogRef = this.dialog.open(InsuranceNowUsersComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '400ms',
    });
  }

  routeToConfigTestData() {
    this.router.navigate(['/testdata']);
  }

  configTestData(index: number): void {
    this.selectedItems = this._productList.dataSource.data[index];
    this.fetchFieldTestDataMap(this.selectedItems.testCaseId);
    this.routeToConfigTestData();
  }

  scheduleTestCase(): void {
    const testCaseSelectedList: string[] = [];
    const clickedRowsArray: TestCaseData[] = Array.from(this._productList.clickedRows);
    for (const testCaseData of clickedRowsArray) {
      testCaseSelectedList.push(testCaseData.testCaseId);
    }
    this._schedulerService.itemsToBeScheduled = testCaseSelectedList;
    this._schedulerService.scheduleCategory = 'Testcases';
    const dialogRef = this.dialog.open(JobschedulerComponent, {
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '400ms',
    });
    this._productList.clickedRows.clear();
  }

  addToBundle(index: number) {
    let testcaseData: TestCaseData;
    testcaseData = this._productList.dataSource.data[index];
    const arrayIndex = this._bundleService.bundledTestCases.findIndex((bundleTestCaseData) =>
      bundleTestCaseData.testCaseId === testcaseData.testCaseId
    );
    if (arrayIndex !== -1) {
      this._bundleService.bundledTestCases.splice(arrayIndex, 1);
    } else {
      this._bundleService.bundledTestCases.push({
        testCaseId: testcaseData.testCaseId,
        testCaseName: testcaseData.testCaseName
      });
    }
  }

  bundleTestCase() {
    this.openBundleAlertDialog();
  }

  openBundleAlertDialog() {
    const dialogRef = this.dialog.open(BundledialogComponent, {
      data: {
        message: this.alertBoxMessage
      }, enterAnimationDuration: '500ms',
      exitAnimationDuration: '400ms',
    });
  }
}

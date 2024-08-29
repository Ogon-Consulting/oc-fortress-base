import { computed, inject, Injectable, signal } from '@angular/core';
import { firstValueFrom } from 'rxjs/internal/firstValueFrom';
import { HttpClient, HttpParams } from '@angular/common/http';
import { TestCaseDataTypeConvertor } from '../../dto/testcasedata.dto';
import { response } from 'express';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Field, Menu, TestCase } from '../../dto/menu.dto';
import { DialogCompComponent } from '../../dialog-comp/dialog-comp.component';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { tap } from 'rxjs';
import { DatePipe } from '@angular/common';
import { InsurancenowuserServiceService } from '../insurancenowuser-service/insurancenowuser-service.service';

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

export interface TestCaseHistory {
  testCaseName: string;
  executedOn: string;
  testStatus: string;
  referenceNumber: string;
  transactionNumber: string;
  executedBy: string;
  product: string;
  transactioncd: string;
  testResult: string;
  jobId: string;
}

export interface Carriers {
  carrierId: string;
  carrierName: string;
}

export interface States {
  stateCode: string;
  stateName: string;
}

export interface Products {
  productCode: string;
  productName: string;
}

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

@Injectable({
  providedIn: 'root'
})

export class TestServiceService {
  #productsList = signal<string[]>([]);
  #selectedCarrierId = signal<string>('');
  #selectedProduct = signal<string>('');
  #selectedstateCd = signal<string>('');
  #lastRunTestCaseData = signal<TestCaseData[]>([]);
  #carriersArray = signal<Carriers[]>([]);
  #carrierStatesArray = signal<States[]>([]);
  #carrierStateProductArray = signal<Products[]>([]);
  #loginUserId = signal<string>('');
  #apiCallSuccess = signal<boolean>(false);
  #showSpinner = signal<boolean>(false);
  #spinnerText = signal<string>('');
  #showTestCaseList = signal<boolean>(false);
  #showTestExecutionSpinner = signal<boolean>(false);
  #spinnerTestExecutionText = signal<string>('');
  listProductApiUrl: string = 'http://localhost:6080/api/v1/getproducts';
  getCarrierStatesAPIURL: string = 'http://localhost:6080/api/v1/getcarrierstates';
  getCarrierStateProductsAPIURL: string = 'http://localhost:6080/api/v1/getcarrierstateproduct';
  fetchTestCasesUrl = 'http://localhost:6080/api/v1/gettestcases';
  listHistoryApiUrl = 'http://localhost:6080/api/v1/gethistory';
  getCarriersAPIURL = 'http://localhost:6080/api/v1/getcarriers';
  refreshCarriersDataAPIURL = 'http://localhost:6080/api/v1/refreshProducts';
  getFieldDefaultTestDataAPIURL = 'http://localhost:6080/api/v1/gettestdatabytestcase';
  createNewTestCaseAPIURL = 'http://localhost:6080/api/v1/createnewtestcase';
  getNextJobIDApiUrl = 'http://localhost:6080/api/v1/getnextjobid';
  appURL = 'http://localhost:6080/api/v1/initiate-test';
  dataValue = '';
  dataValue1 = '';
  #fetchedDataAlready = signal<boolean>(false);
  inputProductSet: boolean = false;
  inputStateCdSet: boolean = false;
  #reloadHistory = signal<boolean>(false);
  testCaseData: TestCaseData[] = [];
  #loadedTestCaseHistory = signal<{ key: string; values: TestCaseHistory[] }[]>([]);
  #uiFieldDefaultValueMap = signal<{ key: string; values: { key: string; values: string } }>({ key: '', values: { key: '', values: '' } });
  testHistoryMap: Map<string, TestCaseHistory[]> = new Map();
  testMapEntries: { key: string, values: TestCaseHistory[] }[] = [];
  dataSource: MatTableDataSource<TestCaseData> = new MatTableDataSource();
  #menuItems = signal<TestCase[]>([]);
  #newTestCaseName = signal<string>('');
  alertBoxMessage = '';
  addtl_message = '';
  alertTestID ='';
  fetchTestCasesAPIStatus: string = '';
  refreshTestCasesList = false;
  clickedRows = new Set<TestCaseData>();
  testExecutionStatus: string = '';
  #executionUserLoginId = signal<string>('');
  #executionUserPassword = signal<string>('');
  #executionUserRole = signal<string>('');

  constructor(private http: HttpClient, public dialog: MatDialog, private router: Router) { }

  set executionUserLoginId(executionUserLoginId: string) {
    this.#executionUserLoginId.set(executionUserLoginId);
  }

  get executionUserLoginId(): string {
    return this.#executionUserLoginId();
  }

  set executionUserPassword(executionUserPassword: string) {
    this.#executionUserPassword.set(executionUserPassword);
  }

  get executionUserPassword(): string {
    return this.#executionUserPassword();
  }

  set executionUserRole(executionUserRole: string) {
    this.#executionUserRole.set(executionUserRole);
  }

  get executionUserRole(): string {
    return this.#executionUserRole();
  }

  async getLastestTestCaseRunData() {
    const ipdatas = {
      product: this.selectedProduct
    };
    let response = await firstValueFrom(this.http.post<any>(this.fetchTestCasesUrl, ipdatas));
    const testDatas = TestCaseDataTypeConvertor(response.testResultEntities);
    this.#lastRunTestCaseData.set(response);
    return response
      ;
  }

  async loadHistories() {
    this.dataValue = '';
    this.testHistoryMap.clear();
    let response = await firstValueFrom(this.http.get<string>(this.listHistoryApiUrl)).then((data) => {
      for (const [key, value] of Object.entries(data)) {
        this.dataValue = value;
      }
      for (const [key, value] of Object.entries(this.dataValue)) {
        this.dataValue1 = value;
        for (const [key, value] of Object.entries(this.dataValue1)) {
          this.testHistoryMap.set(key, JSON.parse(JSON.stringify(value)) as TestCaseHistory[]);
        }
      }
      this.testMapEntries = Array.from(this.testHistoryMap).map(([key, values]) => ({ key, values }));

      this.#loadedTestCaseHistory.set(this.testMapEntries);
    }).catch()
      .finally();
    return [];
  }

  intializeArrays() {
    this.#carrierStatesArray.set([]);
    this.#carrierStateProductArray.set([]);
    this.dataSource.data = [];
  }

  async getAllCarriers() {
    await firstValueFrom(this.http.get<any>(this.getCarriersAPIURL)).then((data) => {
      this.#carriersArray.set(data.carriergroups);
    }).catch((e) => { console.error(e) });
  }

  async getCarrierStates(carrierId: string) {
    let params = new HttpParams();
    params = params.append('carrierId', carrierId);
    await firstValueFrom(this.http.get<any>(this.getCarrierStatesAPIURL, { params })).then((data) => {
      this.#carrierStatesArray.set(data.carrierstates);
    }).catch((e) => { console.error(e) });
  }

  async getCarrierStateProducts(stateCd: string) {
    let params = new HttpParams();
    params = params.append('carrierId', this.selectedCarrierId);
    params = params.append('stateCode', stateCd);
    await firstValueFrom(this.http.get<any>(this.getCarrierStateProductsAPIURL, { params })).then((data) => {
      this.#carrierStateProductArray.set(data.carrierstateproducts);
    }).catch((e) => { console.error(e) });
  }

  async refreshCarriersStatesProducts() {
    await firstValueFrom(this.http.get<any>(this.refreshCarriersDataAPIURL)).then((data) => {
      this.#carriersArray.set(data.carriergroups);
    }).catch((e) => { console.error(e) });
  }

  async fetchFieldTestDataMap(testCaseId: string) {
    let params = new HttpParams();
    params = params.append('carrierId', this.selectedCarrierId);
    params = params.append('stateCd', this.selectedstateCd);
    params = params.append('lob', this.selectedProduct);
    params = params.append('testCaseId', testCaseId);
    await firstValueFrom(this.http.get<any>(this.getFieldDefaultTestDataAPIURL, { params })).then((data) => {
      this.#uiFieldDefaultValueMap.set(data.uifieldtestdatamap);
      const testcase: TestCase[] = [];
      let menu: Menu[] = [];
      for (let [testcasename, menuarray] of Object.entries(this.#uiFieldDefaultValueMap())) {
        const menu: Menu[] = [];
        for (let [key, value] of Object.entries(menuarray)) {
          const fields: Field[] = [];
          for (let [fieldId, subvalue] of Object.entries(value)) {
            let label = '';
            let fieldId = '';
            let defaultValue = '';
            let readOnly = '';
            let newValue = '';
            for (let [fieldName, fieldValue] of Object.entries(subvalue)) {
              if (fieldName === 'label') {
                label = fieldValue;
              } else if (fieldName === 'fieldId') {
                fieldId = fieldValue;
              } else if (fieldName === 'defaultValue') {
                defaultValue = fieldValue;
              } else if (fieldName === 'readOnly') {
                readOnly = fieldValue;
              } else if (fieldName === 'newValue') {
                newValue = '';
              }
            }
            fields.push({
              label: label,
              fieldId: fieldId,
              defaultValue: defaultValue,
              readOnly: readOnly,
              newValue: ''
            })
          }
          menu.push({
            menuName: key,
            fields: fields
          })
        }
        testcase.push({
          testCaseId: testcasename,
          menus: menu
        })
      }
      this.menuItems = testcase;
    }).catch((e) => { console.error(e) });
  }

  async createNewTestData() {
    console.log('this.menuItems', this.menuItems);
    const ipdatas = {
      'carrierId': this.selectedCarrierId,
      'stateCd': this.selectedstateCd,
      'lob': this.selectedProduct,
      'testCaseName': this.newTestCaseName,
      'testCaseData': JSON.stringify(this.menuItems)
    };
    await firstValueFrom(this.http.post<any>(this.createNewTestCaseAPIURL, ipdatas)).then((result) => {
      this.alertBoxMessage = result.message;
      this.openAlertDialog();
      this.refreshTestCasesList = true;
      this.router.navigate(['/home']);
    }).catch((data) => {
      this.alertBoxMessage = 'Facing Exception while creating new test case';
      this.openAlertDialog();
      return 'failed';
    })
  }

  getHistories = computed(() => {
    if (this.#reloadHistory()) {
      this.http.get<string>(this.listHistoryApiUrl).subscribe((data) => {
        for (const [key, value] of Object.entries(data)) {
          this.dataValue = value;
        }
      });
      for (const [key, value] of Object.entries(this.dataValue)) {
        this.dataValue1 = value;
        for (const [key, value] of Object.entries(this.dataValue1)) {
          this.testHistoryMap.set(key, JSON.parse(JSON.stringify(value)) as TestCaseHistory[]);
        }
      }
      this.testMapEntries = Array.from(this.testHistoryMap).map(([key, values]) => ({ key, values }));
    }
    return this.testMapEntries;
  })

  set newTestCaseName(newTestCaseName: string) {
    this.#newTestCaseName.set(newTestCaseName);
  }

  get newTestCaseName(): string {
    return this.#newTestCaseName();
  }

  set menuItems(menuItems: TestCase[]) {
    this.#menuItems.set(menuItems);
  }

  get menuItems(): TestCase[] {
    return this.#menuItems();
  }

  set uiFieldDefaultValueMap(uiFieldDefaultValueMap: { key: string; values: { key: string; values: string } }) {
    this.#uiFieldDefaultValueMap.set(uiFieldDefaultValueMap);
  }

  get uiFieldDefaultValueMap(): { key: string; values: { key: string; values: string } } {
    return this.#uiFieldDefaultValueMap();
  }

  set carrierStateProductArray(carrierStateProductArray: Products[]) {
    this.#carrierStateProductArray.set(carrierStateProductArray);
  }

  get carrierStateProductArray(): Products[] {
    return this.#carrierStateProductArray();
  }


  set carrierStatesArray(carrierStatesArray: States[]) {
    this.#carrierStatesArray.set(carrierStatesArray);
  }

  get carrierStatesArray(): States[] {
    return this.#carrierStatesArray();
  }

  set selectedCarrierId(selectedCarrierId: string) {
    this.#selectedCarrierId.set(selectedCarrierId);
  }

  get selectedCarrierId(): string {
    return this.#selectedCarrierId();
  }

  set carriersArray(carriersArray: Carriers[]) {
    this.#carriersArray.set(carriersArray);
  }

  get carriersArray(): Carriers[] {
    return this.#carriersArray();
  }

  set showTestCaseList(showTestCaseList: boolean) {
    this.#showTestCaseList.set(showTestCaseList);
  }

  get showTestCaseList(): boolean {
    return this.#showTestCaseList();
  }

  set showTestExecutionSpinner(showTestExecutionSpinner: boolean) {
    this.#showTestExecutionSpinner.set(showTestExecutionSpinner);
  }

  get showTestExecutionSpinner(): boolean {
    return this.#showTestExecutionSpinner();
  }

  set spinnerTestExecutionText(spinnerTestExecutionText: string) {
    this.#spinnerTestExecutionText.set(spinnerTestExecutionText);
  }

  get spinnerTestExecutionText(): string {
    return this.#spinnerTestExecutionText();
  }


  set showSpinner(showSpinner: boolean) {
    this.#showSpinner.set(showSpinner);
  }

  get showSpinner(): boolean {
    return this.#showSpinner();
  }

  set spinnerText(spinnerText: string) {
    this.#spinnerText.set(spinnerText);
  }

  get spinnerText(): string {
    return this.#spinnerText();
  }

  set loadedTestCaseHistory(loadedTestCaseHistory: { key: string; values: TestCaseHistory[] }[]) {
    this.#loadedTestCaseHistory.set(loadedTestCaseHistory);
  }

  get loadedTestCaseHistory(): { key: string; values: TestCaseHistory[] }[] {
    return this.#loadedTestCaseHistory();
  }

  set fetchedDataAlready(fetchedDataAlready: boolean) {
    this.#fetchedDataAlready.set(fetchedDataAlready);
  }

  get fetchedDataAlready(): boolean {
    return this.#fetchedDataAlready();
  }

  set reloadHistory(reloadHistory: boolean) {
    this.#reloadHistory.set(reloadHistory);
  }

  get reloadHistory(): boolean {
    return this.#reloadHistory();
  }

  set apiCallSuccess(apiCallSuccess: boolean) {
    this.#apiCallSuccess.set(apiCallSuccess);
  }

  get apiCallSuccess(): boolean {
    return this.#apiCallSuccess();
  }

  set loginUserId(loginUserId: string) {
    this.#loginUserId.set(loginUserId);
  }

  get loginUserId(): string {
    return this.#loginUserId();
  }

  set productsList(productsList: string[]) {
    this.#productsList.set(productsList);
  }

  get productsList() {
    return this.#productsList();
  }

  set selectedProduct(selectedProduct: string) {
    this.#selectedProduct.set(selectedProduct);
  }

  get selectedProduct() {
    return this.#selectedProduct();
  }

  set selectedstateCd(selectedstateCd: string) {
    this.#selectedstateCd.set(selectedstateCd);
  }

  get selectedstateCd() {
    return this.#selectedstateCd();
  }

  set lastRunTestCaseData(lastRunTestCaseData: TestCaseData[]) {
    this.#lastRunTestCaseData.set(lastRunTestCaseData);
  }

  get lastRunTestCaseData() {
    return this.#lastRunTestCaseData();
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


  fetchTestCases() {
    this.spinnerText = 'Fetching Testcases...'
    this.showSpinner = true;
    const ipdatas = {
      carrierId: this.selectedCarrierId,
      stateCd: this.selectedstateCd,
      product: this.selectedProduct
    };
    return this.http.post<any>(this.fetchTestCasesUrl, ipdatas)
      .pipe(
        tap({
          error: error => {
            this.fetchTestCasesAPIStatus = 'Failed';
            this.showSpinner = false;
          }
        })
      )
      .subscribe((data) => {
        this.fetchTestCasesAPIStatus = data.status;
        this.dataSource.data = data.recentruntestdata;
        this.showSpinner = false;
      });
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



  async refreshProducts() {
    this.alertBoxMessage = '';
    this.refreshCarriersStatesProducts();
    this.alertBoxMessage = 'Products Refreshed';
    this.openAlertDialog();
  }

  async getNextJobID() {

    let result = await firstValueFrom(this.http.get<any>(this.getNextJobIDApiUrl)).then((data) => {
      const nextJobID = data.nextjobid.jobId;
      const testCaseSelectedList: string[] = [];
      const clickedRowsArray: TestCaseData[] = Array.from(this.clickedRows);
      for (const testCaseData of clickedRowsArray) {
        testCaseSelectedList.push(testCaseData.testCaseId);
      }
      const ipdatas = {
        nextJobId: nextJobID,
        carrierId: this.#selectedCarrierId(),
        product: this.#selectedProduct(),
        stateCd: this.#selectedstateCd(),
        effectDt: new DatePipe('en-US').transform(new Date(), 'MM/dd/yyyy'),
        testCaseList: testCaseSelectedList,
        executedBy: this.loginUserId,
        loginUserId:this.#executionUserLoginId(),
        loginPassword: this.#executionUserPassword(),
        testData: JSON.stringify(this.menuItems)
      };

      this.spinnerTestExecutionText = 'Testcases Execution inprogress...';
      this.showTestExecutionSpinner = true;
      this.alertBoxMessage = 'Test Execution in Progress for ';
      this.addtl_message = '';
      this.alertTestID = nextJobID;

      this.openAlertDialog();
      this.#showTestExecutionSpinner.set(true);
      this.dataSource.data = this.dataSource.data;

      let result = firstValueFrom(this.http.post<any>(this.appURL, ipdatas)).then((data) => {
        this.testExecutionStatus = data.status;
        this.dataSource.data = data.recentruntestdata;
        this.dataSource.data = this.dataSource.data;
        this.#showTestCaseList.set(true);
        this.clickedRows.clear();
        this.showTestExecutionSpinner = false;
        this.alertBoxMessage = 'Test Execution ' + this.testExecutionStatus + ' for ';
        this.alertTestID = nextJobID;
        this.addtl_message = 'Please check history for more info';
        this.openAlertDialog();
      }).catch(() => {
        this.#showTestExecutionSpinner.set(false);
        this.alertBoxMessage = 'Issues Reported with Test Execution of ';
        this.alertTestID = nextJobID;
        this.addtl_message = 'Please check history for more info';
        this.openAlertDialog();
      })
        .finally(() => {
          this.showTestExecutionSpinner = false;
          this.#showTestExecutionSpinner.set(false);
        });
    }).catch(() => { })
      .finally(() => { });
  }


}

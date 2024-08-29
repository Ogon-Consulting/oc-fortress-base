import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs/internal/firstValueFrom';
import { Bundles, TestCaseMap } from '../../dto/bundle.dto';
import { TestServiceService } from '../testservice/test-service.service';
import { DialogCompComponent } from '../../dialog-comp/dialog-comp.component';
import { LoginService } from '../../../login-module/login.service';
import { InsurancenowuserServiceService } from '../insurancenowuser-service/insurancenowuser-service.service';

export interface TestCases {
  testCaseId: string,
  testCaseName: string
}

const initialTestCaseData: TestCaseMap[] = [];
const initialBundles: Bundles = {
  bundleSelected: false,
  bundleId: 0,
  bundleName: '',
  description: '',
  statusCd: '',
  recentJobId: '',
  lastExecutionResult: '',
  lastExecutedBy: '',
  lastExecutedOn: '',
  testCaseBundle: initialTestCaseData
};
@Injectable({
  providedIn: 'root'
})

export class BundleService {
  getActiveBundlesAPIURL = 'http://localhost:6080/api/v1/getactivebundles';
  addBundleAPIURL = 'http://localhost:6080/api/v1/addbundle';
  executeBundleAPIURL = 'http://localhost:6080/api/v1/executebundlebyid';
  getNextJobIDApiUrl = 'http://localhost:6080/api/v1/getnextjobid';
  deleteBundleAPIURL = 'http://localhost:6080/api/v1/deletebundle';
  #bundlesToBeExecuted = signal<number[]>([]);
  #bundlesList = signal<Bundles[]>([]);
  #bundledTestCases = signal<TestCases[]>([]);
  alertBoxMessage = '';
  _testService = inject(TestServiceService);
  _loginService = inject(LoginService);
  nextJobID = '';
  #carrierId = signal<string>('');
  #product = signal<string>('');
  #stateCd = signal<string>('');
  #executionUserLoginId = signal<string>('');
  #executionUserPassword = signal<string>('');
  #executionUserRole = signal<string>('');

  constructor(private http: HttpClient, public dialog: MatDialog, private router: Router) { }

  set carrierId(carrierId: string) {
    this.#carrierId.set(carrierId);
  }

  get carrierId(): string {
    return this.#carrierId();
  }

  set product(product: string) {
    this.#product.set(product);
  }

  get product(): string {
    return this.#product();
  }

  set stateCd(stateCd: string) {
    this.#stateCd.set(stateCd);
  }

  get stateCd(): string {
    return this.#stateCd();
  }

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

  set bundlesToBeExecuted(bundlesToBeExecuted: number[]) {
    this.#bundlesToBeExecuted.set(bundlesToBeExecuted);
  }

  get bundlesToBeExecuted(): number[] {
    return this.#bundlesToBeExecuted();
  }

  set bundledTestCases(bundledTestCases: TestCases[]) {
    this.#bundledTestCases.set(bundledTestCases);
  }

  get bundledTestCases(): TestCases[] {
    return this.#bundledTestCases();
  }

  set bundlesList(bundlesList: Bundles[]) {
    this.#bundlesList.set(bundlesList);
  }

  get bundlesList(): Bundles[] {
    return this.#bundlesList();
  }

  async getAllActiveBundles() {
    let params = new HttpParams();
    params = params.append('carrierId', this._testService.selectedCarrierId);
    params = params.append('stateCd', this._testService.selectedstateCd);
    params = params.append('lob', this._testService.selectedProduct);
    await firstValueFrom(this.http.get<any>(this.getActiveBundlesAPIURL, { params })).then((data) => {
      this.#bundlesList.set(data.bundles);
    }).catch((error) => {
      console.error(error);
    })
  }

  async addNewBundle(bundleName: string, description: string) {
    const ipdatas = {
      carrierId: this._testService.selectedCarrierId,
      stateCd: this._testService.selectedstateCd,
      lob: this._testService.selectedProduct,
      bundleName: bundleName,
      description: description,
      testCaseBundle: this.bundledTestCases
    };

    await firstValueFrom(this.http.post<any>(this.addBundleAPIURL, ipdatas)).then((data) => {
      this.alertBoxMessage = data.message;
      this.bundledTestCases = [];
      this.openAlertDialog();
    }).catch((error) => {
      console.error(error);
    })
  }

  openAlertDialog() {
    const dialogRef = this.dialog.open(DialogCompComponent, {
      data: {
        message: this.alertBoxMessage
      }, enterAnimationDuration: '500ms',
      exitAnimationDuration: '400ms',
    });
  }

  async executeBundles() {
    this.nextJobID = '';
    await firstValueFrom(this.http.get<any>(this.getNextJobIDApiUrl)).then((data) => {
      this.nextJobID = data.nextjobid.jobId;
      this.invokeExecuteService();
    }).catch((error) => {
      console.error(error);
    });
  }

  async invokeExecuteService() {
    this.alertBoxMessage = 'Job ' + this.nextJobID + ' submitted';
    this.openAlertDialog();
    const ipdatas = {
      carrierId: this._testService.selectedCarrierId,
      product: this._testService.selectedProduct,
      stateCd: this._testService.selectedstateCd,
      executedBy: this._testService.loginUserId,
      loginUserId:this.#executionUserLoginId(),
      loginPassword: this.#executionUserPassword(),
      nextJobId: this.nextJobID,
      bundleIds: this.bundlesToBeExecuted,
    };
    await firstValueFrom(this.http.post<any>(this.executeBundleAPIURL, ipdatas)).then((result) => {
      this.#bundlesToBeExecuted.set([]);
      this.alertBoxMessage = result.status;
      this.openAlertDialog();
      this.getAllActiveBundles();
    }).catch((error) => {
      console.error(error);
    })
  }

  async deleteBundleById(bundleId: number) {
    let params = new HttpParams();
    params = params.append('bundleId', bundleId);
    await firstValueFrom(this.http.get<any>(this.deleteBundleAPIURL, { params })).then((result) => {
      this.alertBoxMessage = result.message;
      this.getAllActiveBundles();
      this.openAlertDialog();
    }).catch((error) => {
      console.error(error);
    })
  }
}

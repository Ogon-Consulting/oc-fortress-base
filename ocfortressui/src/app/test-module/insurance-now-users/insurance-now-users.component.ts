import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogContent, MatDialogActions, MatDialogContainer, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { InsurancenowuserServiceService } from './../services/insurancenowuser-service/insurancenowuser-service.service';
import { Component, inject } from '@angular/core';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { TestServiceService } from '../services/testservice/test-service.service';

@Component({
  selector: 'app-insurance-now-users',
  standalone: true,
  imports: [MatDialogContent, MatDialogActions, MatDialogContainer, MatDialogModule, MatButtonModule, MatFormFieldModule, MatInputModule, FormsModule, MatListModule, MatOptionModule, MatSelectModule],
  templateUrl: './insurance-now-users.component.html',
  styleUrl: './insurance-now-users.component.scss'
})
export class InsuranceNowUsersComponent {

_insuranceNowUserService = inject(InsurancenowuserServiceService);

constructor(private dialogRef: MatDialogRef<InsuranceNowUsersComponent>){
  this.getAllInsuranceNowUsers();
  this.dialogRef.updateSize('22vw', '20vw');
}

getAllInsuranceNowUsers(){
  this._insuranceNowUserService.getAllInsuranceNowUsers();
}

onCancelClick(){
  this.dialogRef.close(null);
}

proceed(){
  this.dialogRef.close(null);
  this._insuranceNowUserService.initiateExecution();
}

}

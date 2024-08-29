import { Component, Inject, } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatDialogContent, MatDialogActions, MatDialogContainer, MatDialogModule, MatDialogConfig } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';


@Component({
  selector: 'app-dialog-comp',
  standalone: true,
  imports: [MatDialogContent, MatDialogActions, MatDialogContainer, MatDialogModule, MatButtonModule],
  templateUrl: './dialog-comp.component.html',
  styleUrl: './dialog-comp.component.scss'
})


export class DialogCompComponent {
  message = '';
  addtl_message = '';
  testid = '';
  cancelButtonText = "Cancel";
  constructor(
    @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<DialogCompComponent>) {
    if (data) {
      this.message = data.message || '';
      this.addtl_message = data.addtl_message || '';
      this.testid = data.testid || ''
      if (data.buttonText) {
        this.cancelButtonText = data.buttonText.cancel || this.cancelButtonText;
        this.dialogRef.updateSize('22vw', '12vw');
      }
    }
    this.dialogRef.updateSize('22vw', '8vw');
  }

  onConfirmClick(): void {
    this.dialogRef.close(true);
  }

}



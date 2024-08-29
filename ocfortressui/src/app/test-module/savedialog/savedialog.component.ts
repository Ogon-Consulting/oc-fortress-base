import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogRef, MatDialogContent, MatDialogActions, MatDialogContainer, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-savedialog',
  standalone: true,
  imports: [MatDialogContent, MatDialogActions, MatDialogContainer, MatDialogModule, MatButtonModule, MatFormFieldModule, MatInputModule, FormsModule],
  templateUrl: './savedialog.component.html',
  styleUrl: './savedialog.component.scss'
})
export class SavedialogComponent {
  cancelButtonText = "Cancel";
  testCaseId = '';
  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private dialogRef: MatDialogRef<SavedialogComponent>) {
    this.dialogRef.updateSize('22vw', '11vw');
  }

  onCancelClick(): void {
    this.dialogRef.close(null);
  }

}

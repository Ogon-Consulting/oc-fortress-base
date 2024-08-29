import { Component, inject, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogContent, MatDialogActions, MatDialogContainer, MatDialogModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BundleService } from '../services/bundleservice/bundle.service';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-bundledialog',
  standalone: true,
  imports: [MatDialogContent, MatDialogActions, MatDialogContainer, MatDialogModule, MatButtonModule, MatFormFieldModule, MatInputModule, FormsModule, MatListModule],
  templateUrl: './bundledialog.component.html',
  styleUrl: './bundledialog.component.scss'
})
export class BundledialogComponent {
  _bundleService = inject(BundleService);
  bundleName = '';
  description = '';

  constructor(private dialogRef: MatDialogRef<BundledialogComponent>) { }

  ngOnInit(): void {
    this.bundleName = '';
    this.description = '';
  }

  onCancelClick(): void {
    this.dialogRef.close(null);
  }

  saveBundle(): void {
    this._bundleService.addNewBundle(this.bundleName, this.description);
    this.dialogRef.close(null);
  }

}

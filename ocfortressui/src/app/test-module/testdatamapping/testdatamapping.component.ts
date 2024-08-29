import { CommonModule } from '@angular/common';
import { Component, inject, NgModule, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, AbstractControl } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatExpansionModule } from '@angular/material/expansion';
import { TestServiceService } from '../services/testservice/test-service.service';
import { MatInput, MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTableModule } from '@angular/material/table';
import { Field, Menu } from '../dto/menu.dto';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { SavedialogComponent } from '../savedialog/savedialog.component';

@Component({
  selector: 'app-testdatamapping',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatExpansionModule, MatInputModule, MatFormFieldModule, MatTableModule, ReactiveFormsModule, MatButtonModule, MatCardModule],
  templateUrl: './testdatamapping.component.html',
  styleUrl: './testdatamapping.component.scss'
})

export class TestdatamappingComponent {
  [x: string]: any;
  expandedPanels: Set<string> = new Set();
  form!: FormGroup;
  _testService = inject(TestServiceService);
  displayedColumns: string[] = ['label', 'defaultValue', 'newValue'];
  testCaseName = '';
  constructor(private fb: FormBuilder, private router: Router, public dialog: MatDialog) { }

  resetNewValue() {
    for (let i = 0; i < this._testService.menuItems.length; i++) {
      let menus: Menu[] = []
      menus = this._testService.menuItems[i].menus;
      for (let j = 0; j < menus.length; j++) {
        let fields: Field[] = [];
        fields = menus[j].fields;
        for (let k = 0; k < fields.length; k++) {
          fields[k].newValue = '';
        }
      }
    }
  }

  getLatestValue(testCaseIndex: number, menuIndex: number, fieldIndex: number, event: Event) {
    const inputElement = event.target as HTMLInputElement;
    const newValue = inputElement.value;
    this._testService.menuItems[testCaseIndex].menus[menuIndex].fields[fieldIndex].newValue = newValue;
  }

  togglePanel(entry: { key: string }): void {
    if (this.isPanelExpanded(entry)) {
      this.expandedPanels.delete(entry.key);
    } else {
      this.expandedPanels.add(entry.key);
    }
  }

  isPanelExpanded(entry: { key: string }): boolean {
    return this.expandedPanels.has(entry.key);
  }

  save() {
    this.openSaveDialog();
  }

  cancel() {
    this.router.navigate(['/home']);
  }

  async openSaveDialog() {
    const dialogRef = this.dialog.open(SavedialogComponent, {
      data: { testCaseName: '' },
      enterAnimationDuration: '500ms',
      exitAnimationDuration: '400ms',
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined && result !== null) {
        this._testService.newTestCaseName = result;
        this._testService.createNewTestData();
      }
    });
  }
}

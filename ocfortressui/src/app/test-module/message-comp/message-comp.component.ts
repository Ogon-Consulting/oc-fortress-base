import { Component } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-message-comp',
  standalone: true,
  imports: [MatCardModule,NgxSpinnerModule,],
  templateUrl: './message-comp.component.html',
  styleUrl: './message-comp.component.scss'
})
export class MessageCompComponent {

  spinnervar!: NgxSpinnerModule;

  constructor(private spinner : NgxSpinnerService){
    this.spinnervar = spinner;
  }

}

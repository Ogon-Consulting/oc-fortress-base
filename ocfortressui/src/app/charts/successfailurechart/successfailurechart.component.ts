import { Component } from '@angular/core';
import { BOPStatus, HOPStatus, overallStatus, PAPStatus } from './data';
import { LegendPosition, NgxChartsModule, ScaleType } from '@swimlane/ngx-charts';

@Component({
  selector: 'app-successfailurechart',
  standalone: true,
  imports: [NgxChartsModule],
  templateUrl: './successfailurechart.component.html',
  styleUrl: './successfailurechart.component.scss'
})
export class SuccessfailurechartComponent {
  overallStatus: any[]=[];
  HOPStatus: any[]=[];
  BOPStatus: any[]=[];
  PAPStatus: any[]=[];
  view: [number, number] = [700, 400];

  // options
  gradient: boolean = true;
  showLegend: boolean = true;
  showLabels: boolean = true;
  isDoughnut: boolean = false;
  legendPosition: LegendPosition = LegendPosition.Right;

  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA'],
    name: 'customScheme',
    selectable: true,
    group: ScaleType.Ordinal
  };

  constructor() {
    Object.assign(this, { overallStatus,HOPStatus,BOPStatus,PAPStatus });
  }

  onSelect(data:any): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data:any): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data:any): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }
}

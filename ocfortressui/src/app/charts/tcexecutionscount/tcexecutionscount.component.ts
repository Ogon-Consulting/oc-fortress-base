import { Component } from '@angular/core';
import { Color, NgxChartsModule, ScaleType } from '@swimlane/ngx-charts';
import { tcExecutionsCount,PAPExecutionsCount } from './data';

@Component({
  selector: 'app-tcexecutionscount',
  standalone: true,
  imports: [NgxChartsModule],
  templateUrl: './tcexecutionscount.component.html',
  styleUrl: './tcexecutionscount.component.scss'
})
export class TcexecutionscountComponent {
  tcExecutionsCount: any[]=[];
  PAPExecutionsCount: any[]=[];
  view: [number, number] = [700, 400];

  // options
  gradient: boolean = true;
  showLegend: boolean = true;
  showLabels: boolean = true;
  isDoughnut: boolean = false;

  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA'],
    name: 'customScheme',
    selectable: true,
    group: ScaleType.Ordinal
  };

  constructor() {
    Object.assign(this, { tcExecutionsCount,PAPExecutionsCount });
  }

  onSelect(data:any): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data:any): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data: any): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }
}

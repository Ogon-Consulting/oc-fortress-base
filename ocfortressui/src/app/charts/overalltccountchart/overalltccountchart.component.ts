import { AfterViewInit, Component, signal } from '@angular/core';
import {
  Color,
  DataItem,
  LegendPosition,
  NgxChartsModule,
  ScaleType,
} from '@swimlane/ngx-charts';
import { testCasesAcrossFunctionalArea as chartData } from './data';

@Component({
  selector: 'app-overalltccountchart',
  standalone: true,
  imports: [NgxChartsModule],
  templateUrl: './overalltccountchart.component.html',
  styleUrl: './overalltccountchart.component.scss'
})
export class OveralltccountchartComponent implements AfterViewInit{
  testCasesAcrossFunctionalArea = signal<DataItem[]>([]);
  view: [number, number] = [500, 400];
  legend: boolean = true;
  legendPosition: LegendPosition = LegendPosition.Right;

  colorScheme: Color = {
    domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5'],
    name: 'customScheme',
    selectable: true,
    group: ScaleType.Ordinal,
  };
  cardColor: string = '#232837';

  ngAfterViewInit(): void {
    this.testCasesAcrossFunctionalArea.set(chartData);
  }

  onSelect(data: any): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data: any): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data: any): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }
}

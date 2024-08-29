import { Component, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Color, NgxChartsModule, ScaleType } from '@swimlane/ngx-charts';
import { tcFeaturesCount } from './data';

@Component({
  selector: 'app-tcfeatures',
  standalone: true,
  imports: [NgxChartsModule],
  templateUrl: './tcfeatures.component.html',
  styleUrl: './tcfeatures.component.scss'
})
export class TcfeaturesComponent {
  tcFeaturesCount: any[]=[];
  view: [number, number] = [700, 400];

  colorScheme: Color = {
    domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5'],
    name: 'customScheme',
    selectable: true,
    group: ScaleType.Ordinal
  };

  cardColor: string = '#232837';

  constructor() {
    Object.assign(this, { tcFeaturesCount });
  }

  onSelect(event: any) {
    console.log(event);
  }
}

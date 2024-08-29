import { Component, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Color, NgxChartsModule, ScaleType } from '@swimlane/ngx-charts';
import { tcProductsCount } from './data'

@Component({
  selector: 'app-tcproductnumbers',
  standalone: true,
  imports: [NgxChartsModule],
  templateUrl: './tcproductnumbers.component.html',
  styleUrl: './tcproductnumbers.component.scss'
})
export class TcproductnumbersComponent {
  tcProductsCount!: any[];
  view: [number, number] = [700, 400];

  // options
  gradient: boolean = false;
  animations: boolean = true;

  colorScheme: Color = {
    domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5'],
    name: 'customScheme',
    selectable: true,
    group: ScaleType.Ordinal
  };

  constructor() {
    Object.assign(this, { tcProductsCount });
  }

  onSelect(event: any) {
    console.log(event);
  }

  // labelFormatting(c:any) {
  //   return `${(c.label)} Population`;
  // }
}

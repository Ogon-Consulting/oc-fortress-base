import { Component } from '@angular/core';
import { TcfeaturesComponent } from '../tcfeatures/tcfeatures.component';
import { TcproductnumbersComponent } from '../tcproductnumbers/tcproductnumbers.component';
import { TcexecutionscountComponent } from '../tcexecutionscount/tcexecutionscount.component';
import { SuccessfailurechartComponent } from '../successfailurechart/successfailurechart.component';
import { OveralltccountchartComponent } from '../overalltccountchart/overalltccountchart.component';

@Component({
  selector: 'app-chartsview',
  standalone: true,
  imports: [TcfeaturesComponent,TcproductnumbersComponent,TcexecutionscountComponent,SuccessfailurechartComponent,OveralltccountchartComponent],
  templateUrl: './chartsview.component.html',
  styleUrl: './chartsview.component.scss'
})
export class ChartsviewComponent {

}

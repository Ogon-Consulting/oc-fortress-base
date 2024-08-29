import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OveralltccountchartComponent } from './overalltccountchart.component';

describe('OveralltccountchartComponent', () => {
  let component: OveralltccountchartComponent;
  let fixture: ComponentFixture<OveralltccountchartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OveralltccountchartComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OveralltccountchartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

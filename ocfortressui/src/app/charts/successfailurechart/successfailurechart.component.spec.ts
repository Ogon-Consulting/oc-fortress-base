import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuccessfailurechartComponent } from './successfailurechart.component';

describe('SuccessfailurechartComponent', () => {
  let component: SuccessfailurechartComponent;
  let fixture: ComponentFixture<SuccessfailurechartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SuccessfailurechartComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SuccessfailurechartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

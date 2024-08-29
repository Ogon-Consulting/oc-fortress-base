import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedulehistoryComponent } from './schedulehistory.component';

describe('SchedulehistoryComponent', () => {
  let component: SchedulehistoryComponent;
  let fixture: ComponentFixture<SchedulehistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SchedulehistoryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SchedulehistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

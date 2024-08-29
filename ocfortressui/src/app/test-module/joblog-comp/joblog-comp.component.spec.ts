import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JoblogCompComponent } from './joblog-comp.component';

describe('JoblogCompComponent', () => {
  let component: JoblogCompComponent;
  let fixture: ComponentFixture<JoblogCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JoblogCompComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(JoblogCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

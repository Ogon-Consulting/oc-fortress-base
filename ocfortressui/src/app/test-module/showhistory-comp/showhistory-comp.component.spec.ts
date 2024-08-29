import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowhistoryCompComponent } from './showhistory-comp.component';

describe('ShowhistoryCompComponent', () => {
  let component: ShowhistoryCompComponent;
  let fixture: ComponentFixture<ShowhistoryCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShowhistoryCompComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ShowhistoryCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

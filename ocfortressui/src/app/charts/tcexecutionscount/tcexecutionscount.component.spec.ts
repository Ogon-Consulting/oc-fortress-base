import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TcexecutionscountComponent } from './tcexecutionscount.component';

describe('TcexecutionscountComponent', () => {
  let component: TcexecutionscountComponent;
  let fixture: ComponentFixture<TcexecutionscountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TcexecutionscountComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TcexecutionscountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

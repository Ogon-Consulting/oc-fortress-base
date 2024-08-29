import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessageCompComponent } from './message-comp.component';

describe('MessageCompComponent', () => {
  let component: MessageCompComponent;
  let fixture: ComponentFixture<MessageCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MessageCompComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MessageCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

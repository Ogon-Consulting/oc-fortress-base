import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TcproductnumbersComponent } from './tcproductnumbers.component';

describe('TcproductnumbersComponent', () => {
  let component: TcproductnumbersComponent;
  let fixture: ComponentFixture<TcproductnumbersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TcproductnumbersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TcproductnumbersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

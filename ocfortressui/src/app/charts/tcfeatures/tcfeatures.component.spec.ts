import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TcfeaturesComponent } from './tcfeatures.component';

describe('TcfeaturesComponent', () => {
  let component: TcfeaturesComponent;
  let fixture: ComponentFixture<TcfeaturesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TcfeaturesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TcfeaturesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

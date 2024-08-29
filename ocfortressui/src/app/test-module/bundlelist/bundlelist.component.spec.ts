import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BundlelistComponent } from './bundlelist.component';

describe('BundlelistComponent', () => {
  let component: BundlelistComponent;
  let fixture: ComponentFixture<BundlelistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BundlelistComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BundlelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BundledialogComponent } from './bundledialog.component';

describe('BundledialogComponent', () => {
  let component: BundledialogComponent;
  let fixture: ComponentFixture<BundledialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BundledialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BundledialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

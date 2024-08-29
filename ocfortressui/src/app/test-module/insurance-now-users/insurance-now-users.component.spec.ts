import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceNowUsersComponent } from './insurance-now-users.component';

describe('InsuranceNowUsersComponent', () => {
  let component: InsuranceNowUsersComponent;
  let fixture: ComponentFixture<InsuranceNowUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InsuranceNowUsersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InsuranceNowUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestdatamappingComponent } from './testdatamapping.component';

describe('TestdatamappingComponent', () => {
  let component: TestdatamappingComponent;
  let fixture: ComponentFixture<TestdatamappingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TestdatamappingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TestdatamappingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

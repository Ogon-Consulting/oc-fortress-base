import { TestBed } from '@angular/core/testing';

import { InsurancenowuserServiceService } from './insurancenowuser-service.service';

describe('InsurancenowuserServiceService', () => {
  let service: InsurancenowuserServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InsurancenowuserServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { SchedulerserviceService } from './schedulerservice.service';

describe('SchedulerserviceService', () => {
  let service: SchedulerserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SchedulerserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

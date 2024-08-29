import { TestBed } from '@angular/core/testing';

import { JoblogService } from './joblog.service';

describe('JoblogService', () => {
  let service: JoblogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JoblogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

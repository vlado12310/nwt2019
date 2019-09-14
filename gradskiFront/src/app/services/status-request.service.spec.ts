import { TestBed } from '@angular/core/testing';

import { StatusRequestService } from './status-request.service';

describe('StatusRequestService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StatusRequestService = TestBed.get(StatusRequestService);
    expect(service).toBeTruthy();
  });
});

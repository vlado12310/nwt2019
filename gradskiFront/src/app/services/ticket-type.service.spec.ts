import { TestBed } from '@angular/core/testing';

import { TicketTypeService } from './ticket-type.service';

describe('TicketTypeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TicketTypeService = TestBed.get(TicketTypeService);
    expect(service).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { PricelistItemService } from './pricelist-item.service';

describe('PricelistItemService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PricelistItemService = TestBed.get(PricelistItemService);
    expect(service).toBeTruthy();
  });
});

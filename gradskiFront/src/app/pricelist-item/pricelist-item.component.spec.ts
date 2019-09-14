import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PricelistItemComponent } from './pricelist-item.component';

describe('PricelistItemComponent', () => {
  let component: PricelistItemComponent;
  let fixture: ComponentFixture<PricelistItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PricelistItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PricelistItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

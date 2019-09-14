import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPricelistItemComponent } from './edit-pricelist-item.component';

describe('EditPricelistItemComponent', () => {
  let component: EditPricelistItemComponent;
  let fixture: ComponentFixture<EditPricelistItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditPricelistItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditPricelistItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

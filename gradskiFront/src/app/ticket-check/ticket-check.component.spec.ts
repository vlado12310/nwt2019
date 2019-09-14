import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketCheckComponent } from './ticket-check.component';

describe('TicketCheckComponent', () => {
  let component: TicketCheckComponent;
  let fixture: ComponentFixture<TicketCheckComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TicketCheckComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketCheckComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

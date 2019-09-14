import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserTicketsComponent } from './user-tickets.component';

describe('UserTicketsComponent', () => {
  let component: UserTicketsComponent;
  let fixture: ComponentFixture<UserTicketsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserTicketsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserTicketsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

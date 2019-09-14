import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LineTimetableComponent } from './line-timetable.component';

describe('LineTimetableComponent', () => {
  let component: LineTimetableComponent;
  let fixture: ComponentFixture<LineTimetableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LineTimetableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LineTimetableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

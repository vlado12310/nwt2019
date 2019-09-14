import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SendStatusRequestComponent } from './send-status-request.component';

describe('SendStatusRequestComponent', () => {
  let component: SendStatusRequestComponent;
  let fixture: ComponentFixture<SendStatusRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SendStatusRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SendStatusRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

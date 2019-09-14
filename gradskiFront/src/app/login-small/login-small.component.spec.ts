import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginSmallComponent } from './login-small.component';

describe('LoginSmallComponent', () => {
  let component: LoginSmallComponent;
  let fixture: ComponentFixture<LoginSmallComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginSmallComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginSmallComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

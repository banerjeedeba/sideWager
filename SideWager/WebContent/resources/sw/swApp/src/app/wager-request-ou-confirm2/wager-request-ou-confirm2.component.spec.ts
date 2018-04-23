import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WagerRequestOuConfirm2Component } from './wager-request-ou-confirm2.component';

describe('WagerRequestOuConfirm2Component', () => {
  let component: WagerRequestOuConfirm2Component;
  let fixture: ComponentFixture<WagerRequestOuConfirm2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WagerRequestOuConfirm2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WagerRequestOuConfirm2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

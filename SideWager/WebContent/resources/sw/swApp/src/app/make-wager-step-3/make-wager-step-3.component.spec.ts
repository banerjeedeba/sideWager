import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeWagerStep3Component } from './make-wager-step-3.component';

describe('MakeWagerStep3Component', () => {
  let component: MakeWagerStep3Component;
  let fixture: ComponentFixture<MakeWagerStep3Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MakeWagerStep3Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MakeWagerStep3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

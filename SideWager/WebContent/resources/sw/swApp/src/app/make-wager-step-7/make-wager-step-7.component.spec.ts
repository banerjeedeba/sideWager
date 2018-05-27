import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeWagerStep7Component } from './make-wager-step-7.component';

describe('MakeWagerStep7Component', () => {
  let component: MakeWagerStep7Component;
  let fixture: ComponentFixture<MakeWagerStep7Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MakeWagerStep7Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MakeWagerStep7Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

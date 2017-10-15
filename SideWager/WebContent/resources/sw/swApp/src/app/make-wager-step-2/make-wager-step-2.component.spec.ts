import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeWagerStep2Component } from './make-wager-step-2.component';

describe('MakeWagerStep2Component', () => {
  let component: MakeWagerStep2Component;
  let fixture: ComponentFixture<MakeWagerStep2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MakeWagerStep2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MakeWagerStep2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

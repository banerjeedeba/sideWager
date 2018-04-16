import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeWagerStep4Component } from './make-wager-step-4.component';

describe('MakeWagerStep4Component', () => {
  let component: MakeWagerStep4Component;
  let fixture: ComponentFixture<MakeWagerStep4Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MakeWagerStep4Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MakeWagerStep4Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

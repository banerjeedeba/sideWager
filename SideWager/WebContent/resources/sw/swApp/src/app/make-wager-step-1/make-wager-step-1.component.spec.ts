import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeWagerStep1Component } from './make-wager-step-1.component';

describe('MakeWagerStep1Component', () => {
  let component: MakeWagerStep1Component;
  let fixture: ComponentFixture<MakeWagerStep1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MakeWagerStep1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MakeWagerStep1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

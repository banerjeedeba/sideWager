import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeWagerStep6Component } from './make-wager-step-6.component';

describe('MakeWagerStep6Component', () => {
  let component: MakeWagerStep6Component;
  let fixture: ComponentFixture<MakeWagerStep6Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MakeWagerStep6Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MakeWagerStep6Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

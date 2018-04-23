import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeWagerStep5LiveComponent } from './make-wager-step-5-live.component';

describe('MakeWagerStep5LiveComponent', () => {
  let component: MakeWagerStep5LiveComponent;
  let fixture: ComponentFixture<MakeWagerStep5LiveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MakeWagerStep5LiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MakeWagerStep5LiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

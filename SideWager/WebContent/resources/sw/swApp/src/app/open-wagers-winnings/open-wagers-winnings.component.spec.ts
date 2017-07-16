import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenWagersWinningsComponent } from './open-wagers-winnings.component';

describe('OpenWagersWinningsComponent', () => {
  let component: OpenWagersWinningsComponent;
  let fixture: ComponentFixture<OpenWagersWinningsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OpenWagersWinningsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpenWagersWinningsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

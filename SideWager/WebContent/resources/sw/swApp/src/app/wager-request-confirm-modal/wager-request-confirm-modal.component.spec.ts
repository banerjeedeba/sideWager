import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WagerRequestConfirmModalComponent } from './wager-request-confirm-modal.component';

describe('WagerRequestConfirmModalComponent', () => {
  let component: WagerRequestConfirmModalComponent;
  let fixture: ComponentFixture<WagerRequestConfirmModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WagerRequestConfirmModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WagerRequestConfirmModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

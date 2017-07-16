import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenWagersLossesComponent } from './open-wagers-losses.component';

describe('OpenWagersLossesComponent', () => {
  let component: OpenWagersLossesComponent;
  let fixture: ComponentFixture<OpenWagersLossesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OpenWagersLossesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpenWagersLossesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

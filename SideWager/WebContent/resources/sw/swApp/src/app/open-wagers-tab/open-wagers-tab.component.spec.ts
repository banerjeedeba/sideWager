import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenWagersTabComponent } from './open-wagers-tab.component';

describe('OpenWagersTabComponent', () => {
  let component: OpenWagersTabComponent;
  let fixture: ComponentFixture<OpenWagersTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OpenWagersTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpenWagersTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

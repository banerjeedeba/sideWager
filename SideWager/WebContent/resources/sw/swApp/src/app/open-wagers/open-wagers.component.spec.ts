import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenWagersComponent } from './open-wagers.component';

describe('OpenWagersComponent', () => {
  let component: OpenWagersComponent;
  let fixture: ComponentFixture<OpenWagersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OpenWagersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpenWagersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

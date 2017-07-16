import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenWagersRequestsComponent } from './open-wagers-requests.component';

describe('OpenWagersRequestsComponent', () => {
  let component: OpenWagersRequestsComponent;
  let fixture: ComponentFixture<OpenWagersRequestsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OpenWagersRequestsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpenWagersRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

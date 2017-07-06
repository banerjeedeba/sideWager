import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SwSideNavCardComponent } from './sw-side-nav-card.component';

describe('SwSideNavCardComponent', () => {
  let component: SwSideNavCardComponent;
  let fixture: ComponentFixture<SwSideNavCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SwSideNavCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SwSideNavCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

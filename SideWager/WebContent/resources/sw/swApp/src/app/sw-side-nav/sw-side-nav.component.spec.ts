import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SwSideNavComponent } from './sw-side-nav.component';

describe('SwSideNavComponent', () => {
  let component: SwSideNavComponent;
  let fixture: ComponentFixture<SwSideNavComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SwSideNavComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SwSideNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SwSideNavListComponent } from './sw-side-nav-list.component';

describe('SwSideNavListComponent', () => {
  let component: SwSideNavListComponent;
  let fixture: ComponentFixture<SwSideNavListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SwSideNavListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SwSideNavListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SwFriendsTabComponent } from './sw-friends-tab.component';

describe('SwFriendsTabComponent', () => {
  let component: SwFriendsTabComponent;
  let fixture: ComponentFixture<SwFriendsTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SwFriendsTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SwFriendsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

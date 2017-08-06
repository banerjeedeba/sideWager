import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyFriendsComponentComponent } from './my-friends-component.component';

describe('MyFriendsComponentComponent', () => {
  let component: MyFriendsComponentComponent;
  let fixture: ComponentFixture<MyFriendsComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyFriendsComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyFriendsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

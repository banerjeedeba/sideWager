import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFriendsComponentComponent } from './add-friends-component.component';

describe('AddFriendsComponentComponent', () => {
  let component: AddFriendsComponentComponent;
  let fixture: ComponentFixture<AddFriendsComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddFriendsComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddFriendsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

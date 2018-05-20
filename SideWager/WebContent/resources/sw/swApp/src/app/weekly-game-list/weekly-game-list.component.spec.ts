import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WeeklyGameListComponent } from './weekly-game-list.component';

describe('WeeklyGameListComponent', () => {
  let component: WeeklyGameListComponent;
  let fixture: ComponentFixture<WeeklyGameListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WeeklyGameListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WeeklyGameListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

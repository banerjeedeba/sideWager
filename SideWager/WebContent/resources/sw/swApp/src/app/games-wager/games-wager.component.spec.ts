import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamesWagerComponent } from './games-wager.component';

describe('GamesWagerComponent', () => {
  let component: GamesWagerComponent;
  let fixture: ComponentFixture<GamesWagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamesWagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamesWagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

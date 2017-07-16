import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveWagersComponent } from './live-wagers.component';

describe('LiveWagersComponent', () => {
  let component: LiveWagersComponent;
  let fixture: ComponentFixture<LiveWagersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LiveWagersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LiveWagersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

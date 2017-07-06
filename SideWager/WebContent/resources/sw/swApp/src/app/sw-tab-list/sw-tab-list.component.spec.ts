import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SwTabListComponent } from './sw-tab-list.component';

describe('SwTabListComponent', () => {
  let component: SwTabListComponent;
  let fixture: ComponentFixture<SwTabListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SwTabListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SwTabListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

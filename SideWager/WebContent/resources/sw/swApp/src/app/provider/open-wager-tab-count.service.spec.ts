import { TestBed, inject } from '@angular/core/testing';

import { OpenWagerTabCountService } from './open-wager-tab-count.service';

describe('OpenWagerTabCountService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OpenWagerTabCountService]
    });
  });

  it('should ...', inject([OpenWagerTabCountService], (service: OpenWagerTabCountService) => {
    expect(service).toBeTruthy();
  }));
});

import { TestBed, inject } from '@angular/core/testing';

import { GamelistService } from './gamelist.service';

describe('GamelistService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GamelistService]
    });
  });

  it('should ...', inject([GamelistService], (service: GamelistService) => {
    expect(service).toBeTruthy();
  }));
});

import { TestBed } from '@angular/core/testing';

import { MedicalrecordsService } from './medicalrecords.service';

describe('MedicalrecordsService', () => {
  let service: MedicalrecordsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MedicalrecordsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

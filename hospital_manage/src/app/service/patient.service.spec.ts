import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { PatientService } from './patient.service';

describe('PatientService', () => {
  let service: PatientService;
  let httpMock: HttpTestingController;
  const baseURL = 'http://localhost:8080/api/patients';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PatientService]
    });
    service = TestBed.inject(PatientService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); 
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should check if patient exists by user ID', () => {
    const userId = 1;

    service.checkPatientExistsByUserId(userId).subscribe(exists => {
      expect(exists).toBeTrue();
    });

    const req = httpMock.expectOne(`${baseURL}/exists/${userId}`);
    expect(req.request.method).toBe('GET');
    req.flush(true); 
  });

});

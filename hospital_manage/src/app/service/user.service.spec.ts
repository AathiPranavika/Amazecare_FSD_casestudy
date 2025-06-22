import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { UserService } from './user.service';
import { User } from '../model/User'; // ✅ Make sure this path is correct

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;
  const baseURL = 'http://localhost:8080/api/users';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); 
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all users', () => {
    const mockUsers: User[] = [
      { userId: 1,name: 'John',email: 'john@example.com',password: 'password123',role: 'DOCTOR',gender: 'Male',dateOfBirth: '1990-01-01',
    contactNumber: '1234567890',createdAt: '2024-06-18T12:00:00Z',}
    ];

    service.getAll().subscribe(users => {
      expect(users.length).toBe(1);
      expect(users).toEqual(mockUsers);
    });

    const req = httpMock.expectOne(`${baseURL}/getAll`);
    expect(req.request.method).toBe('GET');
    req.flush(mockUsers);
  });
});

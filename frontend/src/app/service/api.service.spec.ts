import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { ApiService, LoginResponse, RegisterResponse } from './api.service';
import { HttpErrorResponse } from '@angular/common/http';

describe('ApiService', () => {
  let service: ApiService;
  let httpMock: HttpTestingController;
  const apiUrl = 'http://localhost:8081/api';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ApiService],
    });

    service = TestBed.inject(ApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Ensure no outstanding HTTP requests
  });

  describe('registerUser', () => {
    it('should register a user successfully', () => {
      const mockResponse: RegisterResponse = { id: '1234' };
      const username = 'john';
      const email = 'john@example.com';
      const password = 'password';

      service.registerUser(username, email, password).subscribe((res) => {
        expect(res).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(`${apiUrl}/user`);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual({
        userName: username,
        email: email,
        password: password,
      });

      req.flush(mockResponse); // Simulate successful response
    });

    it('should handle error during registration', () => {
      const username = 'john';
      const email = 'john@example.com';
      const password = 'password';
      const mockError = new HttpErrorResponse({
        status: 400,
        statusText: 'Bad Request',
        error: { error: 'Registration error' },
      });

      service.registerUser(username, email, password).subscribe({
        next: () => fail('Expected error'),
        error: (error) => {
          expect(error).toEqual(mockError);
        },
      });

      const req = httpMock.expectOne(`${apiUrl}/user`);
      expect(req.request.method).toBe('POST');
      req.flush(mockError.error, { status: 400, statusText: 'Bad Request' }); // Simulate error response
    });
  });

  describe('loginUser', () => {
    it('should log in a user successfully', () => {
      const username = 'john';
      const mockResponse: LoginResponse = {
        userName: 'john',
        email: 'john@example.com',
        password: 'password',
      };

      service.loginUser(username).subscribe((res) => {
        expect(res).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(`${apiUrl}/user?userName=${username}`);
      expect(req.request.method).toBe('GET');

      req.flush(mockResponse); // Simulate successful response
    });

    it('should handle error during login', () => {
      const username = 'john';
      const mockError = new HttpErrorResponse({
        status: 404,
        statusText: 'Not Found',
        error: { error: 'User not found' },
      });

      service.loginUser(username).subscribe({
        next: () => fail('Expected error'),
        error: (error) => {
          expect(error).toEqual(mockError);
        },
      });

      const req = httpMock.expectOne(`${apiUrl}/user?userName=${username}`);
      expect(req.request.method).toBe('GET');

      req.flush(mockError.error, { status: 404, statusText: 'Not Found' }); // Simulate error response
    });
  });
});

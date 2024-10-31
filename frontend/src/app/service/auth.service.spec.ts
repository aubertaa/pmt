import { AuthService } from './auth.service';
import { ApiService } from './api.service';
import { Router } from '@angular/router';
import { ToastService } from './toast.service';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

describe('AuthService', () => {
  let authService: AuthService;
  let apiServiceMock: unknown;
  let routerMock: unknown;
  let toastServiceMock: unknown;

  beforeEach(() => {
    // Mock ApiService
    apiServiceMock = {
      loginUser: jest.fn(),
      registerUser: jest.fn(),
      getUsers: jest.fn(),
    };

    // Mock only the necessary Router methods
    routerMock = {
      navigate: jest.fn(),
    };

    // Mock the ToastService
    toastServiceMock = {
      addToast: jest.fn(),
    };

    // Initialize AuthService with the mocked dependencies
    authService = new AuthService(
      apiServiceMock as ApiService,
      routerMock as Router,
      toastServiceMock as ToastService
    );
  });

  describe('login', () => {
    it('should successfully log in the user and navigate to home', () => {
      const username = 'testuser';
      const password = 'testpass';
      const loginResponse = { userName: '', email: '', password: 'testpass' };

      (apiServiceMock as any).loginUser.mockReturnValue(of(loginResponse));

      authService.login(username, password);

      expect((apiServiceMock as any).loginUser).toHaveBeenCalledWith(username);
      expect((toastServiceMock as any).addToast).toHaveBeenCalledWith(
        'Login successful !'
      );
      expect((routerMock as any).navigate).toHaveBeenCalledWith(['/']);
      expect(localStorage.getItem('isLoggedIn')).toBe('true');
    });

    it('should handle login failure when password does not match', () => {
      const username = 'testuser';
      const password = 'wrongpass';
      const loginResponse = { userName: '', email: '', password: 'correctpass' };

      (apiServiceMock as any).loginUser.mockReturnValue(of(loginResponse));

      authService.login(username, password);

      expect((toastServiceMock as any).addToast).toHaveBeenCalledWith('Login failed !');
      expect((routerMock as any).navigate).not.toHaveBeenCalled();
      expect(localStorage.getItem('isLoggedIn')).toBeNull();
    });

    it('should handle login API error', () => {
      const username = 'testuser';
      const password = 'testpass';

      const errorResponse = new HttpErrorResponse({
        error: 'test error',
        status: 500,
      });

      (apiServiceMock as any).loginUser.mockReturnValue(throwError(() => errorResponse));

      authService.login(username, password);

      expect((toastServiceMock as any).addToast).toHaveBeenCalledWith('Login failed !');
      expect((routerMock as any).navigate).not.toHaveBeenCalled();
      expect(localStorage.getItem('isLoggedIn')).toBeNull();
    });
  });

  describe('register', () => {
    it('should successfully register the user and navigate to login page', () => {
      const username = 'testuser';
      const email = 'test@example.com';
      const password = 'testpass';
      const registerResponse = { id: '1' };

      (apiServiceMock as any).registerUser.mockReturnValue(of(registerResponse));

      authService.register(username, email, password);

      expect((apiServiceMock as any).registerUser).toHaveBeenCalledWith(
        username,
        email,
        password
      );
      expect((toastServiceMock as any).addToast).toHaveBeenCalledWith(
        'Registration successful !'
      );
      expect((routerMock as any).navigate).toHaveBeenCalledWith(['/login']);
    });

    it('should handle registration error from API', () => {
      const username = 'testuser';
      const email = 'test@example.com';
      const password = 'testpass';

      const errorResponse = new HttpErrorResponse({
        error: 'test error',
        status: 500,
      });

      (apiServiceMock as any).registerUser.mockReturnValue(
        throwError(() => errorResponse)
      );

      authService.register(username, email, password);

      expect((toastServiceMock as any).addToast).toHaveBeenCalledWith(
        'Registration failed, try another email !'
      );
      expect((routerMock as any).navigate).not.toHaveBeenCalled();
    });
  });

  describe('logout', () => {
    it('should log out the user and remove isLoggedIn from localStorage', () => {
      // Set isLoggedIn to true first
      localStorage.setItem('isLoggedIn', 'true');

      authService.logout();

      expect(localStorage.getItem('isLoggedIn')).toBeNull();
    });
  });

  describe('isAuthenticated', () => {
    it('should return true if isLoggedIn is set to true in localStorage', () => {
      localStorage.setItem('isLoggedIn', 'true');
      expect(authService.isAuthenticated()).toBe(true);
    });

    it('should return false if isLoggedIn is not set in localStorage', () => {
      localStorage.removeItem('isLoggedIn');
      expect(authService.isAuthenticated()).toBe(false);
    });
  });

  describe('getUsers', () => {
    describe('getUsers', () => {
      it('should fetch users successfully and update the usersSubject', () => {
        const usersResponse = [
          { userName: 'user1', email: 'user1@example.com', userId: 1, notifications: true},
          { userName: 'user2', email: 'user2@example.com', userId: 2, notifications: false },
        ];

        (apiServiceMock as any).getUsers.mockReturnValue(of(usersResponse));

        authService.getUsers();

        expect((apiServiceMock as any).getUsers).toHaveBeenCalled();
        expect((toastServiceMock as any).addToast).toHaveBeenCalledWith('Users fetched !');
        expect(authService.usersSubject.value).toEqual(usersResponse);
      });

      it('should handle error when fetching users', () => {
        const errorResponse = new HttpErrorResponse({
          error: 'test error',
          status: 500,
        });

        (apiServiceMock as any).getUsers.mockReturnValue(throwError(() => errorResponse));

        authService.getUsers();

        expect((toastServiceMock as any).addToast).toHaveBeenCalledWith('Users not fetched !');
        expect(authService.usersSubject.value).toEqual([]);
      });
    });
  });
});

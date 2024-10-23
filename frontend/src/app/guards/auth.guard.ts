import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';

export const authGuard: CanActivateFn = async (route) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  console.log(route.url);

  const isLoggedIn: boolean = authService.isAuthenticated();

  console.log('isLoggedIn: ' + isLoggedIn);
  if (route.url.length > 0 && isLoggedIn) {
    console.log('navigate to home');
    router.navigate(['/']);
    return false;
  }
  if (route.url.length === 0 && !isLoggedIn) {
    console.log('navigate to login');
    router.navigate(['/login']);
    return false;
  }
  return true;
};

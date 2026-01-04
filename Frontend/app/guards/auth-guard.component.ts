import { Injectable } from '@angular/core';
import { Router, UrlTree, CanActivate} from '@angular/router';
import { AuthService } from '../services/auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router : Router){}

  canActivate(
  ): boolean | UrlTree {
    if (this.authService.LoggedIn()) {
      this.authService.justLoggedIn = true;
      alert('accès autorisé');
      return true;
    }
    else {
      alert('accès refusé, réessayez');
      return this.router.parseUrl('/connexion');
    }
}
}


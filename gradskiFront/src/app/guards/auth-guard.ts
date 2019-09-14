import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { LoginService } from '../services/login.service';
import { MatSnackBar } from '@angular/material';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private loginService: LoginService,
        private snackBar : MatSnackBar
    ) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if (this.loginService.currentUserValue && this.checkPremmisions(route.data["roles"], this.loginService.currentUserValue.status)) {
            return true;
        }
        this.snackBar.open("You must log in to access this feature!","", {
            duration: 3000,
          });
        this.router.navigate(['/login'], { queryParams: { returnUrl: state }});
        return false;
    }

    checkPremmisions(roles:Array<String>, status : string) : boolean{
        let role : String = status;
        if(status=="ELDERLY" || status=="STUDENT" || status=="STANDARD")
        {
            role = "USER";
        }
        return roles.includes(role);
    }
}
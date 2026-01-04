import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable } from "rxjs";
import { AuthService } from "../services/auth-service.service";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authService: AuthService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(catchError((error) => {
            if (error.status === 401 && !req.url.includes('/login')) {
                // Gérer l'erreur 401 (non autorisé)
                this.authService.seDeconnecter(); // Par exemple, déconnecter l'utilisateur
            }
            throw error;
        }));
    }
}
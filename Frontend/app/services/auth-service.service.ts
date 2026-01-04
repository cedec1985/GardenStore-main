import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterAPIForm } from '../models/user-create.form';
import { Router } from '@angular/router';
import { VerificationResponse } from '../shared/VerificationResponse';

const BASE_URL = 'allow-control-access-origin: http://localhost:8080/client/login';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

private tokenKey = 'ACCESS_TOKEN';
public isAuthenticated: boolean = true;
public justLoggedIn = true;
private isLoggedIn = true;
LoggedIn(): boolean {
  return this.isLoggedIn && this.justLoggedIn;
}
constructor(private http: HttpClient, private router: Router) {
}
  // Simuler le hachage du mot de passe côté client 
  hashPassword(password: string): string {
    return password;
  }

  // Appel à l'API pour enregistrer l'utilisateur avec mot de passe haché
  public register(form: RegisterAPIForm): Observable<any> {
    return this.http.post<any>(BASE_URL, form);
  }

   public loginwithToken(payload:VerificationResponse):void{
        if(payload.tokenValid){
          localStorage.clear();
          localStorage.setItem(this.tokenKey,payload.jwt);
        }
      }
  public registerWithAuth(payload:string): Observable<string> {
    return this.http.post<string>(BASE_URL, payload, { responseType: 'text' as 'json' });
  }

  public loginwithAuth(payload: string): Observable<VerificationResponse> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<VerificationResponse>(
      BASE_URL, payload, httpOptions);
    }

   public registerwithToken (payload:string): void {
    this.registerWithAuth(payload).subscribe(()=>{
      this.router.navigate(['/connexion']);
    });
  }
  login(email: string, password: string): boolean {
    this.isAuthenticated = email === 'cedricdecraim@msn.com' && password === 'Test1234@';
    this.isAuthenticated=true;
    return this.isAuthenticated;
  }

  public seConnecter(){
    localStorage.setItem(this.tokenKey, "access_token");
  }
  public estConnecte() : boolean{
    localStorage.getItem(this.tokenKey) !== null;
    return this.tokenKey!=null && this.tokenKey.length>0;
  }
  public seDeconnecter(){
    this.isAuthenticated = false;
    localStorage.removeItem(this.tokenKey);
  }
  public getToken(): string | null {
    return  localStorage.getItem(this.tokenKey);
  }
  get connectedUserID(){
    return 1;
  }
}

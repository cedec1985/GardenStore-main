import { VerificationResponse } from './../../shared/VerificationResponse';
import { CommonModule, NgIf } from '@angular/common';
import { Component, ElementRef, NO_ERRORS_SCHEMA, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service.service';
import { HomeComponent } from '../home/home.component';

@Component({
  selector: 'app-connexion',
  templateUrl: './connexion.component.html',
  styleUrls: ['./connexion.component.css'],
  imports: [CommonModule,FormsModule,ReactiveFormsModule,NgIf],
  standalone: true,
  schemas: [NO_ERRORS_SCHEMA]
})

export class ConnexionComponent{

  loginForm! :FormGroup;
  data!: Form;
  role: string = '';
  password: string = '';
  cookieService: any;
  email: string ='';
  message: string = '';
  response!:VerificationResponse;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private service: AuthService,
    public el: ElementRef
  ) {
    this.Formulaire();
    if(this.service.estConnecte()){
      this.router.navigate(['/']);
    }
  }

  Formulaire(): void {

   this.loginForm=this.formBuilder.group({

      email: ['', Validators.required,Validators.email],
      password: ['', Validators.required],
    });
  }

  login() {

     if
        (this.loginForm.value.password === 'Test1234@' && this.loginForm.value.email === 'cedricdecraim@msn.com')
        {
      
      this.service.loginwithAuth(this.loginForm.value).subscribe((loginResponse:VerificationResponse)=>{
        this.response=loginResponse;
      });

      this.service.login(this.email, this.password);
      console.log('Login réussi');
      alert('bravo, vous êtes connecté');
      console.log(this.maskPassword(this.loginForm.value.password));
      const jsonData = JSON.stringify(this.email);
      localStorage.setItem('email utilisateur : ', this.loginForm.value.email);
      this.router.navigate(['/']);
      this.service.seConnecter();
      this.loginForm.reset();
       } else {
      console.log('Login échoué');
      alert('échec de la connexion, veuillez réessayer');
      this.loginForm.reset();
    }
      }

       seDeconnecter(){
        this.router.navigate(['/connexion']); //rediriger vers la page 'connexion' si admin pas connecté
        alert("vous êtes déconnecté");
        this.service.seDeconnecter();

    }
      // Fonction pour masquer le mot de passe avec des étoiles
      maskPassword(password: string | undefined): string {
        // Vous pouvez ajuster le nombre d'étoiles en fonction de vos besoins
        return password ? '*'.repeat(password.length) : '';
      }
    }

import { CommonModule, NgIf } from '@angular/common';
import { Component, ElementRef, input, Input, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Category } from '../../models/category';
import { CategoriesmockService } from '../../services/categoriesmock.service';
import { CategoryComponent } from '../category/category.component';
import { CategoryService } from './../../services/categorie.service';
import { ConnexionComponent } from '../connexion/connexion.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule,RouterModule, CategoryComponent, FormsModule, ReactiveFormsModule, NgIf, ConnexionComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {

  @Input() isAuthenticated: boolean = false;
  message!: string;
  category!: any;
  categorie!: any;
  listimages!:any;
  listCategory!: Category[];
  logo!: string;
  urloutillage: string ="";
  urlmobilier:string="";
  urlplantes:string="";
  urlaccessoires:string="";
  emailForm! :string;
  @Input() messageWelcome: string = '';

  constructor(public elementRef: ElementRef,private service : CategoriesmockService, private categorieservice :CategoryService, private router :Router) {
    this.messageWelcome  = 'Connexion réussie !';
  }

   ngOnInit(): void {
    this.message = 'Bienvenue sur Garden Store ! Visitez notre magasin dédié aux articles de jardin !';
    this.logo="assets/gardenstorelogo.png";
    this.urloutillage ="assets/outillage.jpg";
    this.urlmobilier ="assets/mobilier.jpg";
    this.urlplantes ="assets/plantes.jpeg";
    this.urlaccessoires ="assets/accessoires.png";

       this.service.getCategories().subscribe(data=>{
         this.category = data;
      },err=>{
        console.log("Erreur retournée : ",err);
      });

       this.findAllCategories();
    }

   findAllCategories() {
     this.categorieservice.findAllCategories()
       .pipe()
       .subscribe(data => {
         console.log("Catégorie de la base de données : ",data);
         this.listCategory = data;
       }, (error: any) => {
         console.log(error);
       });
   }

 detailCategorie(id:number){

    this.service.getCategorie(id).subscribe(datas=>{
      this.categorie = datas;

      let nomCategorie : string = this.categorie.nom;

        switch (nomCategorie) {
    case 'OUTILLAGE':
        this.router.navigate(["/product/outillage",id]);


        break;
    case 'MOBILIER':
        this.router.navigate(["/product/mobilier",id]);

        break;
    case 'PLANTES':
        this.router.navigate(["product/plantes",id]);

        break;
    case 'ACCESSOIRES':
        this.router.navigate(["product/accessoires",id]);

        break;
    default:
        console.log("il n'existe pas de catégorie !");
        break;
  }
     },
     err=>{
       console.log("Erreur retournée avec la catégorie choisie : ",err);
     }
    );
    }
   login(email: string, password: string): boolean {
    this.isAuthenticated = email === 'cedricdecraim@msn.com' && password === 'Test1234@';
    this.isAuthenticated = true;
    this.elementRef.nativeElement.innerText="cedricdecraim@msn.com"
    return this.isAuthenticated;
  }
}



 import { CommonModule } from '@angular/common';
import { Component, OnInit} from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CategoriesmockService } from '../../../services/categoriesmock.service';
import { ProduitresultComponent } from "../../produitresult/produitresult.component";


    @Component({
    selector: 'app-accessoires',
    standalone: true,
    templateUrl: './accessoires.component.html',
    styleUrl: './accessoires.component.css',
    imports: [CommonModule, RouterModule, ReactiveFormsModule, ProduitresultComponent]
})
    export class AccessoiresComponent implements OnInit {

      id!: number;
      name!: string;
      price!: number;
      reference!: number;
      avis!: string;
      url!: string;
      articleId!: number;
      quantity!: number;
      accessoiresCategorie: any ={};
      categorieTrouve!:any;

      constructor(private router : Router, private categorieService: CategoriesmockService){}

      ngOnInit(): void {
        this.categorieService.getCategorie(4).subscribe(data=>this.accessoiresCategorie=data);
      }

      processReq(message: any){
        console.log("message du composant fils cote pere :",message);
         this.categorieService.getCategoriesByNom(message).subscribe(
           data=>{
           this.categorieTrouve=data;                                            //construire la route vers chaque article de la catégorie accessoires
           let url :string = "/"+this.categorieTrouve[0].nom.toLowerCase();      // on construit un url et on y met le nom de la première catégorie trouvée
           let id :number = this.categorieTrouve[0].id;                          // on y met l'id de la première catégorie trouvée dans l'url
            this.router.navigate([url,id]);                                      //on navigue vers cette url
         },err=>{
             console.log("erreur obtenue :",err);
         });

      }
    }

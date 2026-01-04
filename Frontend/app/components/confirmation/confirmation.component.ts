import { DeliveryBrandPipe } from './../../pipes/pipes/delivery-brand-pipe';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { PanierService } from '../../services/panier.service';
import { CommonModule} from '@angular/common';
import { ReactiveFormsModule, FormsModule, Form } from '@angular/forms';
import { Observable } from 'rxjs';
import { Livreur } from '../../models/livreur';
import { LivreurService } from '../../services/livreur.service';
import { Client } from '../../models/client.model';
import { ClientService } from '../../services/client.service';
import { CommandeService } from '../../services/commande.service';
import { CommandeAPIForm,  CommandeForm } from '../../models/dto/commande.dto';
import { AsyncScheduler } from 'rxjs/internal/scheduler/AsyncScheduler';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css'],
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule],
  standalone: true
})
export class ConfirmationComponent implements OnInit {

  frais:number = 3.00;
  soustotal:number = 0.00;
  cartItems:any[] = [];
  total:number = 0.00;
  lastname!: string;
  firstname!: string;
  adresse!: string;
  email!: string;
  tel!: number;
  numCommande!: any;
  curDate=new Date();
  cb!: any;
  livreur!:number;
  client!:number;
  CommandeData! : Observable<CommandeAPIForm>;
  commande!:Observable<CommandeForm>;
  commande$!:any;
  DeliveryBrand!:any;

  constructor(private panier: PanierService, private route: ActivatedRoute, public $livreur:LivreurService,public $client :ClientService,private commandeService: CommandeService) {
    this.cartItems = this.panier.getItemsFromCart();
    this.cartItems.forEach(element => console.log('elem : ',element));
    this.cartItems.forEach(element => console.log('product price : ',element.price));
    this.cartItems.forEach(element => this.soustotal += element.price);
    this.total = this.soustotal + this.frais; //frais = frais de livraison
    this.cartItems.forEach(element => console.log('le client du produit : ',element.orderedBy));
    this.cartItems.forEach(element => console.log('le fournisseur du produit : ',element.deliveredBy));
    this.DeliveryBrand = this.$livreur.DelivereryBrandName();
   }

  ngOnInit(): void {
    this.route.params.subscribe(params=>{
      this.firstname = params['firstname'];
      this.lastname = params['lastname'];
      this.adresse = params['adr'];
      this.email = params['email'];
      this.tel = params['tel'];
      this.cb = params['cb'];

     })
     console.log('firstname : ',this.firstname);
     console.log('lastname : ',this.lastname);
     this.numCommande = this.generateNumCommande();
     this.EnregisterCommande();
  }

  generateNumCommande() {
    return Math.random().toString().slice(2,11)
  }

//-----------------------------------Enregistrer la commande----------------------------------------------//

  EnregisterCommande(): void {

      const formData: CommandeForm = {...this.commande,

        id:this.commande$,
        montant: this.total,
        quantite: 1,
        dateCommande: new Date(this.curDate),
        nCommande: this.numCommande,
        livreur :new Livreur(1,"dumont","dumxav","xavier","dumxav@gmail.com","DHL"),
        client: new Client(1,"alexandre","kimtsaris",{addresseRue:"rue des marolles",addresseVille:"Paris",addresseNumero:19,addresseCodepostal:1020},"cedricdecraim@msn.com",400000000)
      };
      const CommandeData: CommandeAPIForm = {...this.commande,

        id:formData.id,
        quantité: formData.quantite,
        n_commande: formData.nCommande,
        date_commande: formData.dateCommande,
        montant: formData.montant,
        delivered_by_livreur_id:formData.livreur,
        ordered_by_client_id:formData.client
      };
      console.log('Données transmises:', CommandeData);
      this.commandeService.enregistrerCommande(CommandeData).subscribe(
        (response) => {
          console.log('Commande enregistrée avec succès :', response);
        },
        (error) => {
          console.error('Erreur lors de l\'enregistrement de la commande :', error);
        }
        );
    }
    saveToLocalStorage(response: Form): void {
      // Convertit l'objet JavaScript en chaîne JSON
      const jsonData = JSON.stringify(response);

      // Stocke les données dans le local storage avec une clé spécifique
      localStorage.setItem('données de la commande', jsonData);
    }
  }

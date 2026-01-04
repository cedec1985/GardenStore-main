import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { EmailValidator, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { DeliveryBrandPipe } from '../../pipes/pipes/delivery-brand-pipe';
import { LivreurService } from '../../services/livreur.service';
import { PanierService } from '../../services/panier.service';

@Component({
  selector: 'app-achattermine',
  templateUrl: './achattermine.component.html',
  styleUrls: ['./achattermine.component.css'],
  imports: [CommonModule, ReactiveFormsModule, FormsModule, RouterModule, DeliveryBrandPipe],
  standalone: true
})
export class AchattermineComponent implements OnInit{
  registerForm!: FormGroup;
  submitted = false;
  cartItems: any[] = [];
  frais:number = 3.00;
  soustotal:number = 0.00;
  total:number = 0.00;
  deliveryBrand :any;

  constructor(private formBuilder: FormBuilder,private panier: PanierService, private router: Router, public $livreur: LivreurService) {
    this.cartItems = this.panier.getItemsFromCart();
    this.cartItems.forEach(element => console.log('prix de produit : ',element.price));
    this.cartItems.forEach(element => this.soustotal += element.price);
    this.total = this.soustotal + this.frais;
    this.cartItems.forEach(element => console.log('le client du produit : ',element.orderedBy));
    this.cartItems.forEach(element => console.log('le fournisseur du produit : ',element.deliveredBy));
    this.deliveryBrand = this.$livreur.DelivereryBrandName();
    console.log('Sous total : ',this.soustotal);
    console.log('éléments du panier : ',this.cartItems);
    console.log('le livreur', this.deliveryBrand);

  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [EmailValidator]],   //// pas besoin de Validators.required ici on gère le "vide" dans le validator
      tel: ['', [Validators.required, Validators.pattern(/^[\d]{9}$/)]],
      adr: ['', Validators.required],
      cb: ['', Validators.required, Validators.pattern(/^[\d]{16}$/)]
  });
  }

  get f() { return this.registerForm.controls; }

  onSubmit() {

     if(this.registerForm.valid){
      const firstname = this.registerForm.get('firstName')!.value;
      const lastname = this.registerForm.get('lastName')!.value;
      const email = this.registerForm.get('email')!.value;
      const adresse = this.registerForm.get('adr')!.value;
      const telephone = this.registerForm.get('tel')!.value;
      const cb = this.registerForm.get('cb')!.value;
      this.router.navigate(['/confirmation' , firstname, lastname, email, adresse, telephone,cb]);
      console.log('SUCCESS!! :-)\n\n' + JSON.stringify(this.registerForm.value,null, 4));
       }
       else{
    // Marquer les champs invalides comme touchés pour afficher les messages d'erreur
      this.markFormGroupTouched(this.registerForm);
       }
      this.submitted=true;
  }
  onReset() {
      this.submitted = false;
      this.registerForm.reset();
  }
   markFormGroupTouched(formGroup: FormGroup) {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();

      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }
}

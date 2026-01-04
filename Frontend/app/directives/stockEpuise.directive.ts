import { Directive, ElementRef, Input, OnInit} from '@angular/core';

@Directive({
 selector: '[verificationStock]',
 standalone: true
})
export class StockEpuiseDirective implements OnInit{
  @Input() quantity: number = 0;

  constructor(private elementRef: ElementRef) {}

  ngOnInit() {
    this.verifierStock();
  }

  verifierStock() {
    if (this.quantity > 5) {
      this.elementRef.nativeElement.innerText = 'Stock épuisé';
    } else {
      this.elementRef.nativeElement.innerText = 'En stock';
    }
  }
}


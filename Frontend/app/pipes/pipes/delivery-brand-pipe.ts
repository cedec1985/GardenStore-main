import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'deliveryBrand'
})
export class DeliveryBrandPipe implements PipeTransform {

  transform(brand: string, type: 'img' | 'icon' = 'img'): string {
    if (!brand) return '';

    const normalizedBrand = brand.trim().toLowerCase();

    const brandMap: Record<string, { img: string; icon: string }> = {
      'dhl': {
        img: 'assets/logo-dhl.png',
        icon: 'fa fa-truck-fast text-warning'
      },
      'fedex': {
        img: 'assets/logo-fedex.png',
        icon: 'fa fa-box text-purple'
      },
      'ups': {
        img: 'assets/logo-ups.png',
        icon: 'fa fa-shipping-fast text-brown'
      },
      'chronopost': {
        img: 'assets/logo-chronopost.png',
        icon: 'fa fa-envelope text-primary'
      }
    };

    const brandInfo = brandMap[normalizedBrand];

    if (!brandInfo) {
      // Fallback
      return type === 'img' ? 'assets/freeshipping.png' : 'fa fa-truck text-muted';
    }
    return brandInfo[type];
  }
}

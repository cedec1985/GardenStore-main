import { Directive, ElementRef, Input, OnChanges, Renderer2, SimpleChanges } from '@angular/core';

@Directive({
  selector: '[appMessage]'
})
export class MessageDirective implements OnChanges {
  @Input() appMessage: string = '';

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['appMessage']) {
      this.renderer.setProperty(this.el.nativeElement, 'textContent', this.appMessage);
      this.renderer.setStyle(this.el.nativeElement, 'color', 'green');
      this.renderer.setStyle(this.el.nativeElement, 'fontWeight', 'bold');
    }
  }
}


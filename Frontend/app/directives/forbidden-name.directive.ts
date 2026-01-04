import { Directive, Injectable } from "@angular/core";
import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

@Directive({
  selector: '[appForbiddenName]',
  standalone: true,
})
@Injectable({providedIn:'root'})

export class ForbiddenValidatorDirective {
static forbiddenPasswordValidator: any;

  /** A hero's name can't match the given regular expression */
public forbiddenpasswordValidator(nameRe: RegExp): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const forbidden = nameRe.test(control.value);
    return forbidden ? { forbiddenName: { value: control.value } } : null;
  };
}
}

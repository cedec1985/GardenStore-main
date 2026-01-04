
import { AbstractControl, ValidationErrors } from "@angular/forms";

export function emailValidator(control : AbstractControl):ValidationErrors | null
{
    const value : string = control.value;

    if(!value||value.trim().length === 0){
      return{ email : "Votre email n'est pas valide, elle ne peux pas être vide"}
    }

    if(value.length < 6)
        return { email : "Votre email n'est pas valide, elle ne peux pas contenir moins de 6 caractères"}

    const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/i;
    if(!regex.test(value))
        return { email : "Votre email n'est pas valide, veuillez corriger le format"}
    return null;
}

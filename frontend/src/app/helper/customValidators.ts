import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class CustomValidators {
  static contactValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const validContact = /^(\+91|0)?[6789]\d{9}$/.test(control.value);
      return validContact ? null : { invalidContact: true };
    };
  }

  static usernameValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const validUsername = /^[a-zA-Z0-9._]+$/.test(control.value);
      return validUsername ? null : { invalidUsername: true };
    };
  }

  static noWhitespaceValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const isWhitespace = (control.value || '').trim().length === 0;
      const isValid = !isWhitespace;
      return isValid ? null : { whitespace: true };
    };
  }

  static nameValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const validName = /^[A-Za-z]+(?:\s[A-Za-z]+)*$/.test(control.value);
      return validName ? null : { invalidName: true };
    };
  }
}

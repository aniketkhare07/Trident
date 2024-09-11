import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../../../services/user.service';
import { SessionService } from '../../../../services/session-service.service';
import { User } from '../../../../models/user';
import { CustomValidators } from '../../../../helper/customValidators';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrl: './register-user.component.scss'
})
export class RegisterUserComponent implements OnInit{
  registerForm!: FormGroup;
  user!: User;
  registered: boolean = false;
  
  constructor(private router: Router, private fb: FormBuilder, private userService: UserService,
    private _snackBar: MatSnackBar, private sessionService: SessionService) {
  }

  initRegistrationForm() {
    this.registerForm = this.fb.group({
      name: ['', [Validators.required, CustomValidators.nameValidator()]],
      contact: ['', [Validators.required, CustomValidators.contactValidator()]],
      username: ['', [Validators.required, CustomValidators.usernameValidator(), CustomValidators.noWhitespaceValidator()]],
      password: ['', [Validators.required, CustomValidators.noWhitespaceValidator()]]
    });
  }
  ngOnInit() {
    this.initRegistrationForm();
  }

  register(){
    if(this.sessionService.isLoggedIn() && this.sessionService.getRole() === 'ADMIN'){
      if (this.registerForm.valid) {
        this.user = new User();
        this.user.name = this.registerForm.get('name')?.value;
        this.user.contact = this.registerForm.get('contact')?.value;
        this.user.username = this.registerForm.get('username')?.value;
        this.user.password = this.registerForm.get('password')?.value;
  
        this.userService.register(this.user).subscribe(
          (response: any) => {
            this._snackBar.open("User registered successfully", 'Ok', {
              duration: 5000,
              horizontalPosition: 'center',
              verticalPosition: 'top',
              });
            this.registered = true;
            this.user = response;
            this.registerForm.reset();
          },
          (error) => {
            this.registerForm.reset();
            this._snackBar.open(error.error.message, 'Ok', {
              duration: 5000,
              horizontalPosition: 'center',
              verticalPosition: 'top',
              });}
        );
      } 
    }
  }

  registerAgain() {
    this.registerForm.reset();
    this.registered = !this.registered;
  }


  hide = true;
  clickEvent(event: MouseEvent) {
    this.hide = !this.hide;
    event.stopPropagation();
  }
}

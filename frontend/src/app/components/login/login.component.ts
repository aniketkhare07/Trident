import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JwtAuthenticationRequest } from '../../models/jwtAuthenticationRequest';
import { SessionService } from '../../services/session-service.service';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{
  loginForm!: FormGroup;
  private request!: JwtAuthenticationRequest;

  constructor(private fb: FormBuilder, private router: Router, private authService: AuthService, private sessionService: SessionService, private _snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username : ['', [Validators.required]],
      password : ['', [Validators.required]]
    });
  }

  login(){
    if(this.sessionService.isLoggedIn()){
      this.sessionService.logout();
    }

    if (this.loginForm.valid) {
      this.request = new JwtAuthenticationRequest();
      this.request.username = this.loginForm.get('username')?.value;
      this.request.password = this.loginForm.get('password')?.value;

      this.authService.login(this.request).subscribe(
        (response: any) => {
          this.sessionService.setUsername(response.userDto.username);
          this.sessionService.setRole(response.userDto.role);
          this.sessionService.setToken(response.token);
          this.sessionService.setRefreshToken(response.refreshToken);
          this.sessionService.setName(response.userDto.name);
          const role = response.userDto.role;
          if(role === 'ADMIN') {
            this.router.navigate(['admin']);
          }
          else {
            this.router.navigate(['home']);
          }  
        },
        (error) => { 
          this._snackBar.open(error.error.message, 'Ok', {
            duration: 5000,
            horizontalPosition: 'center',
            verticalPosition: 'top',
        });}
      );
    }
    
  }

  hide = true;
  clickEvent(event: MouseEvent) {
    this.hide = !this.hide;
    event.stopPropagation();
  }
}



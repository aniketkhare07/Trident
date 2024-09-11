import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from '../../services/session-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  name: string | null = '';

  constructor(private router: Router, private sessionService: SessionService,
    private _snackBar: MatSnackBar
  ){
    this.name = sessionService.getName();
  }

  logout(){
    this.sessionService.logout();
    this.router.navigate(['login']);
    this._snackBar.open("Logged out successfully.", 'Ok', {
      duration: 5000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
  });
  }

  navigate() {
    if(this.sessionService.getRole() === 'ADMIN') {
      this.router.navigate(['admin']);
    }
    else {
      this.router.navigate(['home']);
    }
  }
}

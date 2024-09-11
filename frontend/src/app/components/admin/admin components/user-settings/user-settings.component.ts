import { Component, AfterViewInit, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { User } from '../../../../models/user';
import { UserService } from '../../../../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.scss']
})
export class UserSettingsComponent implements OnInit {

  user!: User;
  statusNow!: string;
  dataSource = new MatTableDataSource<User>();
  displayedColumns: string[] = ['userid', 'username', 'contact', 'isActive', 'action'];

  constructor(private router: Router, private userService: UserService, private _snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.userService.fetchUsers().subscribe(
      (response) => { 
        this.dataSource.data = response;
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
          });
      }
    );
  }

  toggleActivation(element: any) {
    this.user = new User();
    this.user.username = element.username;
    this.user.active = !element.active;

    console.log(this.user);
    this.userService.setUserStatus(this.user).subscribe(
      (response) => {
        element.active = !element.active;
        this.statusNow = element.active ? "activated" : "deactivated";
        this._snackBar.open("User "+this.statusNow, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
          });
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
          });
      }
    );
  }
  
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}

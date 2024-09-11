import { Component, signal } from '@angular/core';

export type MenuItem = {
  icon: string,
  label: string,
  route?: string
}

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss',
})
export class AdminComponent {

  menuItems = signal<MenuItem[]>([
    {icon : 'dashboard', label : 'Dashboard', route : 'dashboard'},
    {icon : 'how_to_reg', label : 'Register User', route : 'register-user'},
    {icon : 'manage_accounts', label : 'Users', route : 'user-settings'},
    {icon : 'task', label : 'Requests', route : 'request-approval'},
    {icon : 'group', label : 'Students', route : 'students'},
  ]);

}

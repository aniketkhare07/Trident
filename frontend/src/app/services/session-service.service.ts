import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor() { }

  public setRole(role: any) {
    localStorage.setItem('role', role);
  }

  public getRole() {
    return localStorage.getItem('role');
  }

  public setToken(token: string) {
    localStorage.setItem('token',token);
  }

  public getToken() {
    return localStorage.getItem('token');
  }

  public setRefreshToken(refreshToken: string) {
    localStorage.setItem('refreshToken',refreshToken);
  }

  public getRefreshToken() {
    return localStorage.getItem('refreshToken');
  }
  public setUsername(username: string) {
    localStorage.setItem('username', username);
  }

  public getUsername() {
    return localStorage.getItem('username');
  }

  public setName(name: string) {
    localStorage.setItem('name', name);
  }

  public getName() {
    return localStorage.getItem('name');
  }

  public isLoggedIn() {
    return this.getUsername() && this.getToken();
  }

  public logout() {
    localStorage.clear();
  }
}

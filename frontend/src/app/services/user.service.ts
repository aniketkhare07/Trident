import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userBaseUrl: string  = environment.baseUrl+"/user";
  private adminBaseUrl: string = environment.baseUrl+"/admin";

  constructor(private http: HttpClient) { }
  
  register(user: User){
    return this.http.post<User>(this.adminBaseUrl + "/register", user);
  }

  fetchUsers() {
    return this.http.get<User[]>(this.adminBaseUrl + "/fetch-users");
  }

  setUserStatus(user: User) {
    return this.http.post<User>(this.adminBaseUrl + "/set-status", user);
  }
}

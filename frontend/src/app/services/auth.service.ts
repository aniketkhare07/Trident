import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { JwtAuthenticationRequest } from '../models/jwtAuthenticationRequest';
import { JwtAuthenticationResponse } from '../models/jwtAuthenticationResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl: string  = environment.baseUrl+"/v1/auth/";
  private headers = new HttpHeaders(
    { 'No-Auth':'True'}
  );

  constructor(private http: HttpClient) { }

  login(data: JwtAuthenticationRequest) {
    return this.http.post<JwtAuthenticationResponse>(this.baseUrl+"login", data, {headers: this.headers});
  }
}

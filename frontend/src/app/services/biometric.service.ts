import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BiometricService {

  private userBaseUrl: string  = environment.baseUrl+"/user";
  private adminBaseUrl: string = environment.baseUrl+"/admin";

  constructor(private http: HttpClient) { }
  
  fetchBiometricDetails(studentId: number) {
    let params = new HttpParams().set('studentId', studentId);
    return this.http.post<any>(this.userBaseUrl + "/fingerprints", {}, { params: params });
  }

}

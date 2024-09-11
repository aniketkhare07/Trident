import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CenterRequest } from '../models/centerRequest';
import { Center } from '../models/center';

@Injectable({
  providedIn: 'root'
})
export class CenterService {

  private userBaseUrl: string  = environment.baseUrl+"/user";
  private adminBaseUrl: string = environment.baseUrl+"/admin";

  constructor(private http: HttpClient) { }

  fetchCenter(username: any) {
    let params = new HttpParams().set('username', username);
    return this.http.post<any>(this.userBaseUrl + "/fetch-centers", {}, { params: params });
  }

  fetchAllCenter() {
    return this.http.get<Center[]>(this.adminBaseUrl + "/all-centers");
  }

  requestCenter(centerRequest: CenterRequest) {
    return this.http.post<CenterRequest>(this.userBaseUrl + "/request-center", centerRequest);
  }

  fetchRequestCenters(centerRequest: CenterRequest) {
    return this.http.post<CenterRequest[]>(this.adminBaseUrl + "/center-requests", centerRequest);
  }

  registerCenter(centerRequest: CenterRequest) {
    return this.http.post<CenterRequest>(this.adminBaseUrl + "/register-center", centerRequest);
  }

  rejectCenter(centerRequest: CenterRequest) {
    return this.http.post<CenterRequest>(this.adminBaseUrl + "/reject-center", centerRequest);
  }

  removeCenter(centerRequest: CenterRequest) {
    return this.http.post<CenterRequest>(this.adminBaseUrl + "/remove-center", centerRequest);
  }
}

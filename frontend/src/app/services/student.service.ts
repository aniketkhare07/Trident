import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Student } from '../models/student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  
  private userBaseUrl: string  = environment.baseUrl+"/user";
  private adminBaseUrl: string = environment.baseUrl+"/admin";

  constructor(private http: HttpClient) { }
  
  fetchStudent(batchCode: any) {
    let params = new HttpParams().set('batchCode', batchCode);
    return this.http.post<any>(this.userBaseUrl + "/fetch-students", {}, { params: params });
  }

  fetchAllStudents() {
    return this.http.get<Student[]>(this.adminBaseUrl + "/all-students");
  }
}

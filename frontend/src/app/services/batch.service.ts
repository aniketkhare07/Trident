import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { BatchRequest } from '../models/batchRequest';
import { Batch } from '../models/batch';

@Injectable({
  providedIn: 'root'
})
export class BatchService {
 
  private userBaseUrl: string  = environment.baseUrl+"/user";
  private adminBaseUrl: string = environment.baseUrl+"/admin";

  constructor(private http: HttpClient) { }
  
  fetchBatch(centerCode: any) {
    let params = new HttpParams().set('centerCode', centerCode);
    return this.http.post<any>(this.userBaseUrl + "/fetch-batches", {}, { params: params });
  }

  fetchAllBatches() {
    return this.http.get<Batch[]>(this.adminBaseUrl + "/all-batches");
  }

  requestBatch(batchRequest: BatchRequest) {
    return this.http.post<BatchRequest>(this.userBaseUrl + "/request-batch", batchRequest);
  }

  fetchRequestBatches(batchRequest: BatchRequest) {
    return this.http.post<BatchRequest[]>(this.adminBaseUrl + "/batch-requests", batchRequest);
  }

  createBatch(batchRequest: BatchRequest) {
    return this.http.post<BatchRequest>(this.adminBaseUrl + "/create-batch", batchRequest);
  }

  rejectBatch(batchRequest: BatchRequest) {
    return this.http.post<BatchRequest>(this.adminBaseUrl + "/reject-batch", batchRequest);
  }

  removeBatch(batchRequest: BatchRequest) {
    return this.http.post<BatchRequest>(this.adminBaseUrl + "/remove-batch", batchRequest);
  }

}

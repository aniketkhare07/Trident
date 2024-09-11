import { Component } from '@angular/core';
import { BatchRequest } from '../../../../models/batchRequest';
import { Status } from '../../../../models/status';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { BatchService } from '../../../../services/batch.service';
import { MatSnackBar } from '@angular/material/snack-bar';


const ELEMENT_DATA1:  BatchRequest[] = [
  {
    requestId: 1, username: 'Ayush', centerCode: "12387", batchCode: '223210' , requestDate: new Date("12/03/24"), status: Status.Pending },
  { requestId: 2, username: 'Rohan', centerCode: "12345", batchCode: '343231' , requestDate: new Date("12/03/24"), status: Status.Pending },
  { requestId: 3, username: 'Rihan', centerCode: "17687", batchCode: '423123' , requestDate: new Date("12/03/24"), status: Status.Pending },
  { requestId: 4, username: 'Rohit', centerCode: "16345", batchCode: '431131' , requestDate: new Date("12/03/24"), status: Status.Pending }

]

const ELEMENT_DATA2:  BatchRequest[] = [
  { requestId: 1, username: 'Ayush', centerCode: "12387", batchCode: '543234' , requestDate: new Date("12/03/24"), status: Status.Approved },
  { requestId: 2, username: 'Rohan', centerCode: "12345", batchCode: '554224' , requestDate: new Date("12/03/24"), status: Status.Approved },
  { requestId: 3, username: 'Rihan', centerCode: "17687", batchCode: '123342' , requestDate: new Date("12/03/24"), status: Status.Approved },
  { requestId: 4, username: 'Rohit', centerCode: "16345", batchCode: '542234' , requestDate: new Date("12/03/24"), status: Status.Approved }

]

const ELEMENT_DATA3:  BatchRequest[] = [
  { requestId: 1, username: 'Ayush', centerCode: "12387", batchCode: '653422' , requestDate: new Date("12/03/24"), status: Status.Rejected },
  { requestId: 2, username: 'Rohan', centerCode: "12345", batchCode: '542342' , requestDate: new Date("12/03/24"), status: Status.Rejected },
  { requestId: 3, username: 'Rihan', centerCode: "17687", batchCode: '134234' , requestDate: new Date("12/03/24"), status: Status.Rejected },
  { requestId: 4, username: 'Rohit', centerCode: "16345", batchCode: '432345' , requestDate: new Date("12/03/24"), status: Status.Rejected }

]

@Component({
  selector: 'app-batch-tab',
  templateUrl: './batch-tab.component.html',
  styleUrl: './batch-tab.component.scss'
})
export class BatchTabComponent {

  pendingColumns : string[] = ['username' , 'centerCode' , 'batchCode' , 'requestDate' , 'status' , 'action'];
  pendingDataSource = new MatTableDataSource<BatchRequest>(ELEMENT_DATA1);

  approvedColumns : string[] = ['username' , 'centerCode' , 'batchCode' , 'requestDate' , 'status'];
  approvedDataSource = new MatTableDataSource<BatchRequest>(ELEMENT_DATA2);

  rejectedColumns : string[] = ['username' , 'centerCode' , 'batchCode' , 'requestDate' , 'status' , 'action'];
  rejectedDataSource = new MatTableDataSource<BatchRequest>(ELEMENT_DATA3);

  batchRequest: BatchRequest = new BatchRequest();
  
  constructor(private router: Router, private datePipe: DatePipe, private batchService: BatchService, private _snackBar: MatSnackBar) {}


  ngOnInit(): void {
    this.fetchData();
  }

  formatDate(date: string): string | null {
    return this.datePipe.transform(date, 'dd/MM/yyyy');
  }

  fetchData() {
    this.batchRequest.status = Status.Pending;
    this.batchService.fetchRequestBatches(this.batchRequest).subscribe(
      (response) => {
        this.pendingDataSource.data = response;
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });}
    );
    this.batchRequest.status = Status.Approved;
    this.batchService.fetchRequestBatches(this.batchRequest).subscribe(
      (response) => {
        this.approvedDataSource.data = response;
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });}
    );
    this.batchRequest.status = Status.Rejected;
    this.batchService.fetchRequestBatches(this.batchRequest).subscribe(
      (response) => {
        this.rejectedDataSource.data = response;
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });}
    );
    
  }

  createBatch(element: any) {
    this.batchRequest.batchCode = element.batchCode;
    this.batchRequest.centerCode = element.centerCode;
    this.batchRequest.username = element.username;
    this.batchService.createBatch(this.batchRequest).subscribe(
      (response) => {
        this._snackBar.open("Batch Created Sucessfully", 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });
        this.fetchData();
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });}
    );
  }

  rejectBatch(element: any) {
    this.batchRequest.batchCode = element.batchCode;
    this.batchRequest.centerCode = element.centerCode;
    this.batchRequest.username = element.username;
    this.batchService.rejectBatch(this.batchRequest).subscribe(
      (response) => {
        this._snackBar.open("Batch Rejected", 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });
        this.fetchData();
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });}
    );
  }

  removeBatch(element: any) {
    this.batchRequest.batchCode = element.batchCode;
    this.batchRequest.centerCode = element.centerCode;
    this.batchRequest.username = element.username;
    this.batchService.removeBatch(this.batchRequest).subscribe(
      (response) => {
        this._snackBar.open("Batch request details removed from data", 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });
        this.fetchData();
      },
      (error) => { 
        this._snackBar.open(error.error.message, 'Ok', {
        duration: 5000,
        horizontalPosition: 'center',
        verticalPosition: 'top',
    });}
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.pendingDataSource.filter = filterValue.trim().toLowerCase();
  }

  applyFilter1(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.approvedDataSource.filter = filterValue.trim().toLowerCase();
  }
  applyFilter2(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.rejectedDataSource.filter = filterValue.trim().toLowerCase();
  }
}

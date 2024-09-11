import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { CenterRequest } from '../../../../models/centerRequest';
import { Status } from '../../../../models/status';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { CenterService } from '../../../../services/center.service';
import { Center } from '../../../../models/center';
import { MatSnackBar } from '@angular/material/snack-bar';


const ELEMENT_DATA1:  CenterRequest[] = [
  { requestId: 1, username: 'Ayush', centerCode: "12387", requestDate: new Date("12/03/24"), status: Status.Pending },
  { requestId: 2, username: 'Rohan', centerCode: "12345", requestDate: new Date("12/03/24"), status: Status.Pending },
  { requestId: 3, username: 'Rihan', centerCode: "17687", requestDate: new Date("12/03/24"), status: Status.Pending },
  { requestId: 4, username: 'Rohit', centerCode: "16345", requestDate: new Date("12/03/24"), status: Status.Pending }

]

const ELEMENT_DATA2:  CenterRequest[] = [
  { requestId: 1, username: 'Ayush', centerCode: "12387", requestDate: new Date("12/03/24"), status: Status.Approved },
  { requestId: 2, username: 'Rohan', centerCode: "12345", requestDate: new Date("12/03/24"), status: Status.Approved },
  { requestId: 3, username: 'Rihan', centerCode: "17687", requestDate: new Date("12/03/24"), status: Status.Approved },
  { requestId: 4, username: 'Rohit', centerCode: "16345", requestDate: new Date("12/03/24"), status: Status.Approved }

]

const ELEMENT_DATA3:  CenterRequest[] = [
  { requestId: 1, username: 'Ayush', centerCode: "12387", requestDate: new Date("12/03/24"), status: Status.Rejected },
  { requestId: 2, username: 'Rohan', centerCode: "12345", requestDate: new Date("12/03/24"), status: Status.Rejected },
  { requestId: 3, username: 'Rihan', centerCode: "17687", requestDate: new Date("12/03/24"), status: Status.Rejected },
  { requestId: 4, username: 'Rohit', centerCode: "16345", requestDate: new Date("12/03/24"), status: Status.Rejected }

]

@Component({
  selector: 'app-center-tab',
  templateUrl: './center-tab.component.html',
  styleUrl: './center-tab.component.scss',
  providers: [DatePipe]
})
export class CenterTabComponent {
  

  pendingColumns : string[] = ['username' , 'centerCode' , 'requestDate' , 'status' , 'action'];
  pendingDataSource = new MatTableDataSource<CenterRequest>(ELEMENT_DATA1);

  approvedColumns : string[] = ['username' , 'centerCode' , 'requestDate' , 'status'];
  approvedDataSource = new MatTableDataSource<CenterRequest>(ELEMENT_DATA2);

  rejectedColumns : string[] = ['username' , 'centerCode' , 'requestDate' , 'status' , 'action'];
  rejectedDataSource = new MatTableDataSource<CenterRequest>(ELEMENT_DATA3);

  centerRequest: CenterRequest = new CenterRequest();
  
  constructor(private router: Router, private datePipe: DatePipe, private centerService: CenterService, private _snackBar: MatSnackBar) {}


  ngOnInit(): void {
    this.fetchData();
  }

  formatDate(date: string): string | null {
    return this.datePipe.transform(date, 'dd/MM/yyyy');
  }

  fetchData() {
    this.centerRequest.status = Status.Pending;
    this.centerService.fetchRequestCenters(this.centerRequest).subscribe(
      (response) => {
        this.pendingDataSource.data = response;
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
        duration: 5000,
        horizontalPosition: 'center',
        verticalPosition: 'top',
        });
      }
    );
    this.centerRequest.status = Status.Approved;
    this.centerService.fetchRequestCenters(this.centerRequest).subscribe(
      (response) => {
        this.approvedDataSource.data = response;
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
        duration: 5000,
        horizontalPosition: 'center',
        verticalPosition: 'top',
        });
      }
    );
    this.centerRequest.status = Status.Rejected;
    this.centerService.fetchRequestCenters(this.centerRequest).subscribe(
      (response) => {
        this.rejectedDataSource.data = response;
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

  registerCenter(element: any) {
    this.centerRequest.centerCode = element.centerCode;
    this.centerRequest.username = element.username;
    this.centerService.registerCenter(this.centerRequest).subscribe(
      (response) => {
        this._snackBar.open("Center registered successfully.", 'Ok', {
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
        });
      }
    );
  }

  rejectCenter(element: any) {
    this.centerRequest.centerCode = element.centerCode;
    this.centerRequest.username = element.username;
    this.centerService.rejectCenter(this.centerRequest).subscribe(
      (response) => {
        this._snackBar.open("Center Request rejected.", 'Ok', {
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
        });
      }
    );
  }

  removeCenter(element: any) {
    this.centerRequest.centerCode = element.centerCode;
    this.centerRequest.username = element.username;
    this.centerService.removeCenter(this.centerRequest).subscribe(
      (response) => {
        this._snackBar.open("Center Request details removed successfully.", 'Ok', {
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
          });
      }
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

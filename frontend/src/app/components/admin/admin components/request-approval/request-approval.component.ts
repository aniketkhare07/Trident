import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { CenterRequest } from '../../../../models/centerRequest';
import { DatePipe } from '@angular/common';
import { CenterService } from '../../../../services/center.service';
import { Center } from '../../../../models/center';
import { Status } from '../../../../models/status';


export interface PeriodicElement {
  id: number;
  name: string;
  centerCode: number;
  batchCode: number;
  action : boolean;
}


const ELEMENT_DATA: PeriodicElement[] = [
  { id: 1, name: 'Ayush', centerCode: 12387, batchCode:  65432, action: false },
  { id: 2, name: 'Rohan', centerCode: 12345, batchCode: 67898, action: false },
  { id: 3, name: 'Rihan', centerCode: 17687, batchCode:  62232, action: false },
  { id: 4, name: 'Rohit', centerCode: 16345, batchCode: 67898, action: false }
];

const ELEMENT_DATA1:  CenterRequest[] = [
  { requestId: 1, username: 'Ayush', centerCode: "12387", requestDate: new Date("12/03/24"), status: Status.Pending },
  { requestId: 2, username: 'Rohan', centerCode: "12345", requestDate: new Date("12/03/24"), status: Status.Pending },
  { requestId: 3, username: 'Rihan', centerCode: "17687", requestDate: new Date("12/03/24"), status: Status.Pending },
  { requestId: 4, username: 'Rohit', centerCode: "16345", requestDate: new Date("12/03/24"), status: Status.Pending }

]

@Component({
  selector: 'app-request-approval',
  templateUrl: './request-approval.component.html',
  styleUrl: './request-approval.component.scss',
  providers: [DatePipe]
})
export class RequestApprovalComponent implements OnInit {

  displayedColumns: string[] = ['id', 'username', 'centerCode', 'batchCode', 'action'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);

  constructor(private router: Router, private datePipe: DatePipe, private centerService: CenterService) {}


  ngOnInit(): void {
  }

}

import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../../services/user.service';
import { CenterService } from '../../../../services/center.service';
import { BatchService } from '../../../../services/batch.service';
import { StudentService } from '../../../../services/student.service';
import { User } from '../../../../models/user';
import { SessionService } from '../../../../services/session-service.service';
import { Center } from '../../../../models/center';
import { Batch } from '../../../../models/batch';
import { Student } from '../../../../models/student';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent implements OnInit{
  
  totalUsers: number = 0;
  totalCenters: number = 0;
  totalBatches: number = 0;
  totalStudents: number = 0;
  userList!: User [];
  centerList!: Center [];
  batchList!: Batch [];
  studentList!: Student[];

  constructor(
    private userService: UserService,
    private centerService: CenterService,
    private batchService: BatchService,
    private studentService: StudentService,
    private sessionService: SessionService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.userService.fetchUsers().subscribe(
      (response) => {this.userList = response
        this.totalUsers = this.userList.length;
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });}
    );

    this.centerService.fetchAllCenter().subscribe(
      (response) => {this.centerList = response
        this.totalCenters = this.centerList.length;
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });}
    );

    this.batchService.fetchAllBatches().subscribe(
      (response) => {this.batchList = response
        this.totalBatches = this.batchList.length;
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });}
    );

    this.studentService.fetchAllStudents().subscribe(
      (response) => {this.studentList = response
        this.totalStudents = this.studentList.length;
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });}
    );
  }
}

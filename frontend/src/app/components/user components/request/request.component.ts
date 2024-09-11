import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CenterService } from '../../../services/center.service';
import { Center } from '../../../models/center';
import { SessionService } from '../../../services/session-service.service';
import { CenterRequest } from '../../../models/centerRequest';
import { BatchRequest } from '../../../models/batchRequest';
import { BatchService } from '../../../services/batch.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrl: './request.component.scss'
})
export class RequestComponent implements OnInit {

  centerForm!: FormGroup;
  batchForm!: FormGroup;
  username!: string | null;
  centerList!: Center[];
  centerRequest!: CenterRequest;
  batchRequest!: BatchRequest;
  centerSubmitted: Boolean = false;
  batchSubmitted: Boolean = false;

  constructor(private fb: FormBuilder, private centerService: CenterService, private batchService: BatchService,
     private sessionService: SessionService, private _snackBar: MatSnackBar) {
    this.username = sessionService.getUsername();
    this.initForms();
  }

  ngOnInit() {
    // Fetch center list and set initial value for centers
    this.centerService.fetchCenter(this.username).subscribe(
      (response: Center[]) => {
        this.centerList = response;
        if (this.centerList.length > 0) {
          this.batchForm.patchValue({ username: this.username, centers: this.centerList[0].centerCode });
        }
      },
      (error) => { 
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      }); }
    );
  }

  initForms() {
    //Init center Form
    this.centerForm = this.fb.group({
      username: ['', Validators.required],
      centerCode: ['', Validators.required]
    });

    this.centerForm.patchValue({ username: this.username });
    this.centerForm.get('username')?.disable();

    this.batchForm = this.fb.group({
      username: ['', Validators.required],
      centerCode: ['', Validators.required],
      batchCode: ['', Validators.required]
    });
    this.batchForm.get('username')?.disable();
  }

  requestCenter() {
    if (this.sessionService.isLoggedIn()) {
      if (this.centerForm.valid) {
        this.centerRequest = new CenterRequest();
        this.centerRequest.username = this.centerForm.get('username')?.value;
        this.centerRequest.centerCode = this.centerForm.get('centerCode')?.value;

        this.centerService.requestCenter(this.centerRequest).subscribe(
          (response: CenterRequest) => {
            this._snackBar.open("Center Requested Sucessfully", 'Ok', {
              duration: 5000,
              horizontalPosition: 'center',
              verticalPosition: 'top',
          });
            this.centerForm.get('centerCode')?.reset();
          },
          (error) => { 
            this._snackBar.open(error.error.message, 'Ok', {
              duration: 5000,
              horizontalPosition: 'center',
              verticalPosition: 'top',
          }); }
        );
      }
    }
  }

  requestBatch() {
    if (this.sessionService.isLoggedIn()) {
      if (this.batchForm.valid) {
        this.batchRequest = new BatchRequest();
        this.batchRequest.username = this.batchForm.get('username')?.value;
        this.batchRequest.centerCode = this.batchForm.get('centerCode')?.value;
        this.batchRequest.batchCode = this.batchForm.get('batchCode')?.value;

        this.batchService.requestBatch(this.batchRequest).subscribe(
          (response: BatchRequest) => {
            this._snackBar.open("Batch Requested Sucessfully", 'Ok', {
              duration: 5000,
              horizontalPosition: 'center',
              verticalPosition: 'top',
          });
            this.batchForm.get('centerCode')?.reset();
            this.batchForm.get('batchCode')?.reset();
          },
          (error) => { 
            this._snackBar.open(error.error.message, 'Ok', {
              duration: 5000,
              horizontalPosition: 'center',
              verticalPosition: 'top',
          }); }
        );
      }
    }
  }

}

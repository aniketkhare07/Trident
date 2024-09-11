import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Batch } from '../../../../models/batch';
import { Center } from '../../../../models/center';
import { Student } from '../../../../models/student';
import { BatchService } from '../../../../services/batch.service';
import { CenterService } from '../../../../services/center.service';
import { StudentService } from '../../../../services/student.service';
import { User } from '../../../../models/user';
import { UserService } from '../../../../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrl: './students.component.scss'
})
export class StudentsComponent {
  dataSelectionForm: FormGroup;
  userList!: User[];
  centerList!: Center[];
  batchList!: Batch[];
  dataSource = new MatTableDataSource<Student>();
  displayedColumns: string[] = ['id', 'name', 'aadhar', 'finger'];
  private localStorageKey = 'student_component_state';

  username!: string | null;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private userService: UserService,
    private centerService: CenterService,
    private batchService: BatchService,
    private studentService: StudentService,
    private _snackBar: MatSnackBar
  ) {
    this.username = localStorage.getItem('username');
    this.dataSelectionForm = this.fb.group({
      users: ['', Validators.required],
      centers: ['', Validators.required],
      batches: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    // Check if there is a saved state in local storage
    const savedState = localStorage.getItem(this.localStorageKey);
    if (savedState) {
      const state = JSON.parse(savedState);
      this.dataSelectionForm.patchValue(state.formValues);
      this.userList = state.userList;
      this.centerList = state.centerList;
      this.batchList = state.batchList;
      this.dataSource.data = state.dataSource;
    } else {
      // Fetch center list and set initial value for centers if not restored from local storage
      this.fetchUserAndInitialState();
    }

    // Subscribe to route changes to save state when navigating away
    this.route.queryParams.subscribe(params => {
      if (params && params['fromDisplayPrints']) {
        // Restore state from local storage if coming back from display-prints
        const savedState = localStorage.getItem(this.localStorageKey);
        if (savedState) {
          const state = JSON.parse(savedState);
          this.dataSelectionForm.patchValue(state.formValues);
          this.userList = state.userList;
          this.centerList = state.centerList;
          this.batchList = state.batchList;
          this.dataSource.data = state.dataSource;
        }
      }
    });
  }

  fetchUserAndInitialState(): void {
    // Fetch user list and set initial value for users
    this.userService.fetchUsers().subscribe(
      (response: User[]) => {
        this.userList = response;
        if (this.userList.length > 0) {
          this.dataSelectionForm.patchValue({ users: this.userList[0].username });
          this.onUserSelected(); // Call the method to fetch centers for the initial user
        }
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

  onUserSelected(): void {
    // Fetch center list and set initial value for centers
    const selectedUsername = this.dataSelectionForm.get('users')?.value;
    this.centerService.fetchCenter(selectedUsername).subscribe(
      (response: Center[]) => {
        this.centerList = response;
        if (this.centerList.length > 0) {
          this.dataSelectionForm.patchValue({ centers: this.centerList[0].centerCode });
          this.onCenterSelected(); // Call the method to fetch batches for the initial center
        }
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

  onCenterSelected(): void {
    const selectedCenterCode = this.dataSelectionForm.get('centers')?.value;
    this.batchService.fetchBatch(selectedCenterCode).subscribe(
      (response: Batch[]) => {
        this.batchList = response;
        if (this.batchList.length > 0) {
          this.dataSelectionForm.patchValue({ batches: this.batchList[0].batchCode });
          this.onBatchSelected(); // Call the method to fetch students for the initial batch
        }
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

  onBatchSelected(): void {
    const selectedBatchCode = this.dataSelectionForm.get('batches')?.value;
    this.studentService.fetchStudent(selectedBatchCode).subscribe(
      (response) => {
        this.dataSource.data = response;
        // Save current state to local storage
        this.saveStateToLocalStorage();
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

  saveStateToLocalStorage(): void {
    const state = {
      formValues: this.dataSelectionForm.value,
      userList: this.userList,
      centerList: this.centerList,
      batchList: this.batchList,
      dataSource: this.dataSource.data
    };
    localStorage.setItem(this.localStorageKey, JSON.stringify(state));
  }

  viewFinger(studentId: number): void {
    // Before navigating, save the current state to local storage
    this.saveStateToLocalStorage();
    this.router.navigate(['display-prints'], { queryParams: { studentid: studentId } });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}

import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { CenterService } from '../../services/center.service';
import { Center } from '../../models/center';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BatchService } from '../../services/batch.service';
import { Batch } from '../../models/batch';
import { StudentService } from '../../services/student.service';
import { Student } from '../../models/student';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  dataSelectionForm: FormGroup;
  centerList!: Center[];
  batchList!: Batch[];
  dataSource = new MatTableDataSource<Student>();
  displayedColumns: string[] = ['id', 'name', 'aadhar', 'finger'];
  private localStorageKey = 'home_component_state';

  username!: string | null;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private centerService: CenterService,
    private batchService: BatchService,
    private studentService: StudentService,
    private _snackBar: MatSnackBar
  ) {
    this.username = localStorage.getItem('username');
    this.dataSelectionForm = this.fb.group({
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
      this.centerList = state.centerList;
      this.batchList = state.batchList;
      this.dataSource.data = state.dataSource;
    } else {
      // Fetch center list and set initial value for centers if not restored from local storage
      this.fetchCenterAndInitialState();
    }

    // Subscribe to route changes to save state when navigating away
    this.route.queryParams.subscribe(params => {
      if (params && params['fromDisplayPrints']) {
        // Restore state from local storage if coming back from display-prints
        const savedState = localStorage.getItem(this.localStorageKey);
        if (savedState) {
          const state = JSON.parse(savedState);
          this.dataSelectionForm.patchValue(state.formValues);
          this.centerList = state.centerList;
          this.batchList = state.batchList;
          this.dataSource.data = state.dataSource;
        }
      }
    });
  }

  fetchCenterAndInitialState(): void {
    // Fetch center list and set initial value for centers
    this.centerService.fetchCenter(this.username).subscribe(
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
      }); }
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
      }); }
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
      }); }
    );
  }

  saveStateToLocalStorage(): void {
    const state = {
      formValues: this.dataSelectionForm.value,
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
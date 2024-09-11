import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BiometricService } from '../../../services/biometric.service';
import { Biometrics } from '../../../models/biometrics';
import { MatSnackBar } from '@angular/material/snack-bar';

export interface Element {
  id: number,
  image: string;
}

@Component({
  selector: 'app-display-prints',
  templateUrl: './display-prints.component.html',
  styleUrl: './display-prints.component.scss'
})
export class DisplayPrintsComponent {
  studentId!: number;
  biometrics!: Biometrics;
  slides: Element[] = [];
  currentSlide: number = 0;

  constructor(private route: ActivatedRoute, private biometricService: BiometricService, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    // Retrieve the studentid from the query parameters
    this.route.queryParams.subscribe(params => {
      this.studentId = +params['studentid'];
    });

    this.biometricService.fetchBiometricDetails(this.studentId).subscribe(
      (response: Biometrics) => {
        this.biometrics = response;
        this.slides = [
          {id: 1, image: this.biometrics.fingerprint1},
          {id: 2, image: this.biometrics.fingerprint2},
          {id: 3, image: this.biometrics.fingerprint3},
          {id: 4, image: this.biometrics.fingerprint4},
          {id: 5, image: this.biometrics.fingerprint5},
          // Add more slides as needed
        ];
      },
      (error) => {
        this._snackBar.open(error.error.message, 'Ok', {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
      });}
    );
  }

  prevSlide(): void {
    this.currentSlide = (this.currentSlide > 0) ? this.currentSlide - 1 : this.slides.length - 1;
  }

  nextSlide(): void {
    this.currentSlide = (this.currentSlide < this.slides.length - 1) ? this.currentSlide + 1 : 0;
  }

}

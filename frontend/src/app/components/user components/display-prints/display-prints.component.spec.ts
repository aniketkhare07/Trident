import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayPrintsComponent } from './display-prints.component';

describe('DisplayPrintsComponent', () => {
  let component: DisplayPrintsComponent;
  let fixture: ComponentFixture<DisplayPrintsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DisplayPrintsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DisplayPrintsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BatchTabComponent } from './batch-tab.component';

describe('BatchTabComponent', () => {
  let component: BatchTabComponent;
  let fixture: ComponentFixture<BatchTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BatchTabComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BatchTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

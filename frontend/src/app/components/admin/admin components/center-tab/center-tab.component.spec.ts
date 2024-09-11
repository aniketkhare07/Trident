import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CenterTabComponent } from './center-tab.component';

describe('CenterTabComponent', () => {
  let component: CenterTabComponent;
  let fixture: ComponentFixture<CenterTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CenterTabComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CenterTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

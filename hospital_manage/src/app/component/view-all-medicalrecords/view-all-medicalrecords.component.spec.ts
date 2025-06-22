import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllMedicalrecordsComponent } from './view-all-medicalrecords.component';

describe('ViewAllMedicalrecordsComponent', () => {
  let component: ViewAllMedicalrecordsComponent;
  let fixture: ComponentFixture<ViewAllMedicalrecordsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewAllMedicalrecordsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAllMedicalrecordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

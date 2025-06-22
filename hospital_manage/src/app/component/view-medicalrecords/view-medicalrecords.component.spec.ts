import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewMedicalrecordsComponent } from './view-medicalrecords.component';

describe('ViewMedicalrecordsComponent', () => {
  let component: ViewMedicalrecordsComponent;
  let fixture: ComponentFixture<ViewMedicalrecordsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewMedicalrecordsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewMedicalrecordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

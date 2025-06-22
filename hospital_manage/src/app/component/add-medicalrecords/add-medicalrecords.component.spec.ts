import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMedicalrecordsComponent } from './add-medicalrecords.component';

describe('AddMedicalrecordsComponent', () => {
  let component: AddMedicalrecordsComponent;
  let fixture: ComponentFixture<AddMedicalrecordsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddMedicalrecordsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddMedicalrecordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

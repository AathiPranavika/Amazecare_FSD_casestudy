import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateMedicalrecordsComponent } from './update-medicalrecords.component';

describe('UpdateMedicalrecordsComponent', () => {
  let component: UpdateMedicalrecordsComponent;
  let fixture: ComponentFixture<UpdateMedicalrecordsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateMedicalrecordsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateMedicalrecordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagePrescriptionComponent } from './manage-prescription.component';

describe('ManagePrescriptionComponent', () => {
  let component: ManagePrescriptionComponent;
  let fixture: ComponentFixture<ManagePrescriptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagePrescriptionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagePrescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

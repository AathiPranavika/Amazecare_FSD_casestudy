import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientMessagingComponent } from './patient-messaging.component';

describe('PatientMessagingComponent', () => {
  let component: PatientMessagingComponent;
  let fixture: ComponentFixture<PatientMessagingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientMessagingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientMessagingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

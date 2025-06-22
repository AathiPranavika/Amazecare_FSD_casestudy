import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorMessagingComponent } from './doctor-messaging.component';

describe('DoctorMessagingComponent', () => {
  let component: DoctorMessagingComponent;
  let fixture: ComponentFixture<DoctorMessagingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorMessagingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DoctorMessagingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

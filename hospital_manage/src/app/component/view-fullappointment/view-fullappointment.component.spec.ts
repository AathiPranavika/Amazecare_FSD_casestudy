import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewFullappointmentComponent } from './view-fullappointment.component';

describe('ViewFullappointmentComponent', () => {
  let component: ViewFullappointmentComponent;
  let fixture: ComponentFixture<ViewFullappointmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewFullappointmentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewFullappointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

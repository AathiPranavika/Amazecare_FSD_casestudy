import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientDashboardComponent } from './patient-dashboard.component';
import { HttpClientTestingModule } from '@angular/common/http/testing'; 
describe('PatientDashboardComponent', () => {
  let component: PatientDashboardComponent;
  let fixture: ComponentFixture<PatientDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientDashboardComponent ],
            imports: [ HttpClientTestingModule ] 

    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

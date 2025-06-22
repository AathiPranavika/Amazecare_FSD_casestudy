import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayAllpatientsComponent } from './display-allpatients.component';

describe('DisplayAllpatientsComponent', () => {
  let component: DisplayAllpatientsComponent;
  let fixture: ComponentFixture<DisplayAllpatientsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisplayAllpatientsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisplayAllpatientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

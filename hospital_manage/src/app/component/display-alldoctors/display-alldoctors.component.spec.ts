import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayAlldoctorsComponent } from './display-alldoctors.component';

describe('DisplayAlldoctorsComponent', () => {
  let component: DisplayAlldoctorsComponent;
  let fixture: ComponentFixture<DisplayAlldoctorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisplayAlldoctorsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisplayAlldoctorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

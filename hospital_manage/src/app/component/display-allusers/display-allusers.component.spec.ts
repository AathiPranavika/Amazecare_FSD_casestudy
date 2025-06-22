import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayAllusersComponent } from './display-allusers.component';

describe('DisplayAllusersComponent', () => {
  let component: DisplayAllusersComponent;
  let fixture: ComponentFixture<DisplayAllusersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisplayAllusersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisplayAllusersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

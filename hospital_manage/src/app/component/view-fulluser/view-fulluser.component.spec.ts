import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewFulluserComponent } from './view-fulluser.component';

describe('ViewFulluserComponent', () => {
  let component: ViewFulluserComponent;
  let fixture: ComponentFixture<ViewFulluserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewFulluserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewFulluserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

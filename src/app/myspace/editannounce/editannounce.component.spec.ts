import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditannounceComponent } from './editannounce.component';

describe('EditannounceComponent', () => {
  let component: EditannounceComponent;
  let fixture: ComponentFixture<EditannounceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditannounceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditannounceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

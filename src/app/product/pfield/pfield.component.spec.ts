import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PfieldComponent } from './pfield.component';

describe('PfieldComponent', () => {
  let component: PfieldComponent;
  let fixture: ComponentFixture<PfieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PfieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PfieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

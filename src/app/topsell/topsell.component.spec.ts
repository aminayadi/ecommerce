import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopsellComponent } from './topsell.component';

describe('TopsellComponent', () => {
  let component: TopsellComponent;
  let fixture: ComponentFixture<TopsellComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TopsellComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopsellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

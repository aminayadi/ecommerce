import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyannouncesComponent } from './myannounces.component';

describe('MyannouncesComponent', () => {
  let component: MyannouncesComponent;
  let fixture: ComponentFixture<MyannouncesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyannouncesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyannouncesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

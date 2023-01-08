import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PfieldDetailComponent } from './pfield-detail.component';

describe('Pfield Management Detail Component', () => {
  let comp: PfieldDetailComponent;
  let fixture: ComponentFixture<PfieldDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PfieldDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pfield: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(PfieldDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PfieldDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pfield on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pfield).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});

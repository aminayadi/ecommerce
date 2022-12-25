import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FavorisDetailComponent } from './favoris-detail.component';

describe('Favoris Management Detail Component', () => {
  let comp: FavorisDetailComponent;
  let fixture: ComponentFixture<FavorisDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FavorisDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ favoris: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(FavorisDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FavorisDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load favoris on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.favoris).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});

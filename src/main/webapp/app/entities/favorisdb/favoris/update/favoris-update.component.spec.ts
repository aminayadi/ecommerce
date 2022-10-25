import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FavorisFormService } from './favoris-form.service';
import { FavorisService } from '../service/favoris.service';
import { IFavoris } from '../favoris.model';

import { FavorisUpdateComponent } from './favoris-update.component';

describe('Favoris Management Update Component', () => {
  let comp: FavorisUpdateComponent;
  let fixture: ComponentFixture<FavorisUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let favorisFormService: FavorisFormService;
  let favorisService: FavorisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FavorisUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FavorisUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FavorisUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    favorisFormService = TestBed.inject(FavorisFormService);
    favorisService = TestBed.inject(FavorisService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const favoris: IFavoris = { id: 'CBA' };

      activatedRoute.data = of({ favoris });
      comp.ngOnInit();

      expect(comp.favoris).toEqual(favoris);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFavoris>>();
      const favoris = { id: 'ABC' };
      jest.spyOn(favorisFormService, 'getFavoris').mockReturnValue(favoris);
      jest.spyOn(favorisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ favoris });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: favoris }));
      saveSubject.complete();

      // THEN
      expect(favorisFormService.getFavoris).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(favorisService.update).toHaveBeenCalledWith(expect.objectContaining(favoris));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFavoris>>();
      const favoris = { id: 'ABC' };
      jest.spyOn(favorisFormService, 'getFavoris').mockReturnValue({ id: null });
      jest.spyOn(favorisService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ favoris: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: favoris }));
      saveSubject.complete();

      // THEN
      expect(favorisFormService.getFavoris).toHaveBeenCalled();
      expect(favorisService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFavoris>>();
      const favoris = { id: 'ABC' };
      jest.spyOn(favorisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ favoris });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(favorisService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

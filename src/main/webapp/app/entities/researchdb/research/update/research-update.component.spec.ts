import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ResearchFormService } from './research-form.service';
import { ResearchService } from '../service/research.service';
import { IResearch } from '../research.model';

import { ResearchUpdateComponent } from './research-update.component';

describe('Research Management Update Component', () => {
  let comp: ResearchUpdateComponent;
  let fixture: ComponentFixture<ResearchUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let researchFormService: ResearchFormService;
  let researchService: ResearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ResearchUpdateComponent],
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
      .overrideTemplate(ResearchUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResearchUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    researchFormService = TestBed.inject(ResearchFormService);
    researchService = TestBed.inject(ResearchService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const research: IResearch = { id: 'CBA' };

      activatedRoute.data = of({ research });
      comp.ngOnInit();

      expect(comp.research).toEqual(research);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResearch>>();
      const research = { id: 'ABC' };
      jest.spyOn(researchFormService, 'getResearch').mockReturnValue(research);
      jest.spyOn(researchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ research });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: research }));
      saveSubject.complete();

      // THEN
      expect(researchFormService.getResearch).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(researchService.update).toHaveBeenCalledWith(expect.objectContaining(research));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResearch>>();
      const research = { id: 'ABC' };
      jest.spyOn(researchFormService, 'getResearch').mockReturnValue({ id: null });
      jest.spyOn(researchService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ research: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: research }));
      saveSubject.complete();

      // THEN
      expect(researchFormService.getResearch).toHaveBeenCalled();
      expect(researchService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IResearch>>();
      const research = { id: 'ABC' };
      jest.spyOn(researchService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ research });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(researchService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

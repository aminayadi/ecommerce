import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FieldsFormService } from './fields-form.service';
import { FieldsService } from '../service/fields.service';
import { IFields } from '../fields.model';
import { ICategory } from 'app/entities/categorydb/category/category.model';
import { CategoryService } from 'app/entities/categorydb/category/service/category.service';

import { FieldsUpdateComponent } from './fields-update.component';

describe('Fields Management Update Component', () => {
  let comp: FieldsUpdateComponent;
  let fixture: ComponentFixture<FieldsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fieldsFormService: FieldsFormService;
  let fieldsService: FieldsService;
  let categoryService: CategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FieldsUpdateComponent],
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
      .overrideTemplate(FieldsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FieldsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fieldsFormService = TestBed.inject(FieldsFormService);
    fieldsService = TestBed.inject(FieldsService);
    categoryService = TestBed.inject(CategoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Category query and add missing value', () => {
      const fields: IFields = { id: 'CBA' };
      const category: ICategory = { id: 'd0cf0b7e-6456-4173-8749-a55451aed340' };
      fields.category = category;

      const categoryCollection: ICategory[] = [{ id: '6d55c54a-b747-4503-9e1d-3c07c2c36298' }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fields });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        categoryCollection,
        ...additionalCategories.map(expect.objectContaining)
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fields: IFields = { id: 'CBA' };
      const category: ICategory = { id: '2e61b7a8-8f38-4ee2-ba52-c68fa370ed5e' };
      fields.category = category;

      activatedRoute.data = of({ fields });
      comp.ngOnInit();

      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.fields).toEqual(fields);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFields>>();
      const fields = { id: 'ABC' };
      jest.spyOn(fieldsFormService, 'getFields').mockReturnValue(fields);
      jest.spyOn(fieldsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fields });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fields }));
      saveSubject.complete();

      // THEN
      expect(fieldsFormService.getFields).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fieldsService.update).toHaveBeenCalledWith(expect.objectContaining(fields));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFields>>();
      const fields = { id: 'ABC' };
      jest.spyOn(fieldsFormService, 'getFields').mockReturnValue({ id: null });
      jest.spyOn(fieldsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fields: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fields }));
      saveSubject.complete();

      // THEN
      expect(fieldsFormService.getFields).toHaveBeenCalled();
      expect(fieldsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFields>>();
      const fields = { id: 'ABC' };
      jest.spyOn(fieldsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fields });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fieldsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategory', () => {
      it('Should forward to categoryService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(categoryService, 'compareCategory');
        comp.compareCategory(entity, entity2);
        expect(categoryService.compareCategory).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

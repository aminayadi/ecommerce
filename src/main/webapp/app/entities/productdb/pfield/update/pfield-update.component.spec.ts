import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PfieldFormService } from './pfield-form.service';
import { PfieldService } from '../service/pfield.service';
import { IPfield } from '../pfield.model';
import { IProduct } from 'app/entities/productdb/product/product.model';
import { ProductService } from 'app/entities/productdb/product/service/product.service';

import { PfieldUpdateComponent } from './pfield-update.component';

describe('Pfield Management Update Component', () => {
  let comp: PfieldUpdateComponent;
  let fixture: ComponentFixture<PfieldUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pfieldFormService: PfieldFormService;
  let pfieldService: PfieldService;
  let productService: ProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PfieldUpdateComponent],
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
      .overrideTemplate(PfieldUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PfieldUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pfieldFormService = TestBed.inject(PfieldFormService);
    pfieldService = TestBed.inject(PfieldService);
    productService = TestBed.inject(ProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Product query and add missing value', () => {
      const pfield: IPfield = { id: 'CBA' };
      const product: IProduct = { id: 'a525ddee-6835-473f-bee3-04e4292ed144' };
      pfield.product = product;

      const productCollection: IProduct[] = [{ id: '953ceb6d-7b65-491c-9e1d-b9cba0c669c9' }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pfield });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProducts.map(expect.objectContaining)
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pfield: IPfield = { id: 'CBA' };
      const product: IProduct = { id: 'c7e5fc79-93f1-4ab6-a905-0c53ce3c3ad2' };
      pfield.product = product;

      activatedRoute.data = of({ pfield });
      comp.ngOnInit();

      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.pfield).toEqual(pfield);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPfield>>();
      const pfield = { id: 'ABC' };
      jest.spyOn(pfieldFormService, 'getPfield').mockReturnValue(pfield);
      jest.spyOn(pfieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pfield });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pfield }));
      saveSubject.complete();

      // THEN
      expect(pfieldFormService.getPfield).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pfieldService.update).toHaveBeenCalledWith(expect.objectContaining(pfield));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPfield>>();
      const pfield = { id: 'ABC' };
      jest.spyOn(pfieldFormService, 'getPfield').mockReturnValue({ id: null });
      jest.spyOn(pfieldService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pfield: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pfield }));
      saveSubject.complete();

      // THEN
      expect(pfieldFormService.getPfield).toHaveBeenCalled();
      expect(pfieldService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPfield>>();
      const pfield = { id: 'ABC' };
      jest.spyOn(pfieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pfield });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pfieldService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProduct', () => {
      it('Should forward to productService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(productService, 'compareProduct');
        comp.compareProduct(entity, entity2);
        expect(productService.compareProduct).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

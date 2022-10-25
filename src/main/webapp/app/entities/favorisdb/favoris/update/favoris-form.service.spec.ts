import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../favoris.test-samples';

import { FavorisFormService } from './favoris-form.service';

describe('Favoris Form Service', () => {
  let service: FavorisFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FavorisFormService);
  });

  describe('Service methods', () => {
    describe('createFavorisFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFavorisFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idproduct: expect.any(Object),
            iduser: expect.any(Object),
            createdat: expect.any(Object),
            modifiedat: expect.any(Object),
            deletedat: expect.any(Object),
          })
        );
      });

      it('passing IFavoris should create a new form with FormGroup', () => {
        const formGroup = service.createFavorisFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idproduct: expect.any(Object),
            iduser: expect.any(Object),
            createdat: expect.any(Object),
            modifiedat: expect.any(Object),
            deletedat: expect.any(Object),
          })
        );
      });
    });

    describe('getFavoris', () => {
      it('should return NewFavoris for default Favoris initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFavorisFormGroup(sampleWithNewData);

        const favoris = service.getFavoris(formGroup) as any;

        expect(favoris).toMatchObject(sampleWithNewData);
      });

      it('should return NewFavoris for empty Favoris initial value', () => {
        const formGroup = service.createFavorisFormGroup();

        const favoris = service.getFavoris(formGroup) as any;

        expect(favoris).toMatchObject({});
      });

      it('should return IFavoris', () => {
        const formGroup = service.createFavorisFormGroup(sampleWithRequiredData);

        const favoris = service.getFavoris(formGroup) as any;

        expect(favoris).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFavoris should not enable id FormControl', () => {
        const formGroup = service.createFavorisFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFavoris should disable id FormControl', () => {
        const formGroup = service.createFavorisFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../pfield.test-samples';

import { PfieldFormService } from './pfield-form.service';

describe('Pfield Form Service', () => {
  let service: PfieldFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PfieldFormService);
  });

  describe('Service methods', () => {
    describe('createPfieldFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPfieldFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            value: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });

      it('passing IPfield should create a new form with FormGroup', () => {
        const formGroup = service.createPfieldFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            type: expect.any(Object),
            value: expect.any(Object),
            product: expect.any(Object),
          })
        );
      });
    });

    describe('getPfield', () => {
      it('should return NewPfield for default Pfield initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPfieldFormGroup(sampleWithNewData);

        const pfield = service.getPfield(formGroup) as any;

        expect(pfield).toMatchObject(sampleWithNewData);
      });

      it('should return NewPfield for empty Pfield initial value', () => {
        const formGroup = service.createPfieldFormGroup();

        const pfield = service.getPfield(formGroup) as any;

        expect(pfield).toMatchObject({});
      });

      it('should return IPfield', () => {
        const formGroup = service.createPfieldFormGroup(sampleWithRequiredData);

        const pfield = service.getPfield(formGroup) as any;

        expect(pfield).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPfield should not enable id FormControl', () => {
        const formGroup = service.createPfieldFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPfield should disable id FormControl', () => {
        const formGroup = service.createPfieldFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

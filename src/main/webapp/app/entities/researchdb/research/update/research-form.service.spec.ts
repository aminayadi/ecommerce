import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../research.test-samples';

import { ResearchFormService } from './research-form.service';

describe('Research Form Service', () => {
  let service: ResearchFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResearchFormService);
  });

  describe('Service methods', () => {
    describe('createResearchFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createResearchFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            iduser: expect.any(Object),
            idcategory: expect.any(Object),
            idzone: expect.any(Object),
            createdat: expect.any(Object),
            updatedat: expect.any(Object),
            zone: expect.any(Object),
          })
        );
      });

      it('passing IResearch should create a new form with FormGroup', () => {
        const formGroup = service.createResearchFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            iduser: expect.any(Object),
            idcategory: expect.any(Object),
            idzone: expect.any(Object),
            createdat: expect.any(Object),
            updatedat: expect.any(Object),
            zone: expect.any(Object),
          })
        );
      });
    });

    describe('getResearch', () => {
      it('should return NewResearch for default Research initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createResearchFormGroup(sampleWithNewData);

        const research = service.getResearch(formGroup) as any;

        expect(research).toMatchObject(sampleWithNewData);
      });

      it('should return NewResearch for empty Research initial value', () => {
        const formGroup = service.createResearchFormGroup();

        const research = service.getResearch(formGroup) as any;

        expect(research).toMatchObject({});
      });

      it('should return IResearch', () => {
        const formGroup = service.createResearchFormGroup(sampleWithRequiredData);

        const research = service.getResearch(formGroup) as any;

        expect(research).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IResearch should not enable id FormControl', () => {
        const formGroup = service.createResearchFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewResearch should disable id FormControl', () => {
        const formGroup = service.createResearchFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

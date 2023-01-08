import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../msg.test-samples';

import { MsgFormService } from './msg-form.service';

describe('Msg Form Service', () => {
  let service: MsgFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MsgFormService);
  });

  describe('Service methods', () => {
    describe('createMsgFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMsgFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            from: expect.any(Object),
            fromUserName: expect.any(Object),
            message: expect.any(Object),
          })
        );
      });

      it('passing IMsg should create a new form with FormGroup', () => {
        const formGroup = service.createMsgFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            from: expect.any(Object),
            fromUserName: expect.any(Object),
            message: expect.any(Object),
          })
        );
      });
    });

    describe('getMsg', () => {
      it('should return NewMsg for default Msg initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMsgFormGroup(sampleWithNewData);

        const msg = service.getMsg(formGroup) as any;

        expect(msg).toMatchObject(sampleWithNewData);
      });

      it('should return NewMsg for empty Msg initial value', () => {
        const formGroup = service.createMsgFormGroup();

        const msg = service.getMsg(formGroup) as any;

        expect(msg).toMatchObject({});
      });

      it('should return IMsg', () => {
        const formGroup = service.createMsgFormGroup(sampleWithRequiredData);

        const msg = service.getMsg(formGroup) as any;

        expect(msg).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMsg should not enable id FormControl', () => {
        const formGroup = service.createMsgFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMsg should disable id FormControl', () => {
        const formGroup = service.createMsgFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

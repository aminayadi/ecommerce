import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MsgFormService } from './msg-form.service';
import { MsgService } from '../service/msg.service';
import { IMsg } from '../msg.model';

import { MsgUpdateComponent } from './msg-update.component';

describe('Msg Management Update Component', () => {
  let comp: MsgUpdateComponent;
  let fixture: ComponentFixture<MsgUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let msgFormService: MsgFormService;
  let msgService: MsgService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MsgUpdateComponent],
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
      .overrideTemplate(MsgUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MsgUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    msgFormService = TestBed.inject(MsgFormService);
    msgService = TestBed.inject(MsgService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const msg: IMsg = { id: 'CBA' };

      activatedRoute.data = of({ msg });
      comp.ngOnInit();

      expect(comp.msg).toEqual(msg);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMsg>>();
      const msg = { id: 'ABC' };
      jest.spyOn(msgFormService, 'getMsg').mockReturnValue(msg);
      jest.spyOn(msgService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ msg });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: msg }));
      saveSubject.complete();

      // THEN
      expect(msgFormService.getMsg).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(msgService.update).toHaveBeenCalledWith(expect.objectContaining(msg));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMsg>>();
      const msg = { id: 'ABC' };
      jest.spyOn(msgFormService, 'getMsg').mockReturnValue({ id: null });
      jest.spyOn(msgService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ msg: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: msg }));
      saveSubject.complete();

      // THEN
      expect(msgFormService.getMsg).toHaveBeenCalled();
      expect(msgService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMsg>>();
      const msg = { id: 'ABC' };
      jest.spyOn(msgService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ msg });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(msgService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MsgDetailComponent } from './msg-detail.component';

describe('Msg Management Detail Component', () => {
  let comp: MsgDetailComponent;
  let fixture: ComponentFixture<MsgDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MsgDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ msg: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(MsgDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MsgDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load msg on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.msg).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});

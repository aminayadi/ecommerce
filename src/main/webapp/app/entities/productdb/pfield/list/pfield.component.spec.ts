import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PfieldService } from '../service/pfield.service';

import { PfieldComponent } from './pfield.component';

describe('Pfield Management Component', () => {
  let comp: PfieldComponent;
  let fixture: ComponentFixture<PfieldComponent>;
  let service: PfieldService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'pfield', component: PfieldComponent }]), HttpClientTestingModule],
      declarations: [PfieldComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(PfieldComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PfieldComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PfieldService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.pfields?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to pfieldService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getPfieldIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPfieldIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

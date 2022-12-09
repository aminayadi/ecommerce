import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FieldsService } from '../service/fields.service';

import { FieldsComponent } from './fields.component';

describe('Fields Management Component', () => {
  let comp: FieldsComponent;
  let fixture: ComponentFixture<FieldsComponent>;
  let service: FieldsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'fields', component: FieldsComponent }]), HttpClientTestingModule],
      declarations: [FieldsComponent],
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
      .overrideTemplate(FieldsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FieldsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FieldsService);

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
    expect(comp.fields?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to fieldsService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getFieldsIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getFieldsIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

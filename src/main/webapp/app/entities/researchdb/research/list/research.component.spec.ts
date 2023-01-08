import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ResearchService } from '../service/research.service';

import { ResearchComponent } from './research.component';

describe('Research Management Component', () => {
  let comp: ResearchComponent;
  let fixture: ComponentFixture<ResearchComponent>;
  let service: ResearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'research', component: ResearchComponent }]), HttpClientTestingModule],
      declarations: [ResearchComponent],
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
      .overrideTemplate(ResearchComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResearchComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ResearchService);

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
    expect(comp.research?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to researchService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getResearchIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getResearchIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

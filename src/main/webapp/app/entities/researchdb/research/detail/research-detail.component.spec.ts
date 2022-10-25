import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResearchDetailComponent } from './research-detail.component';

describe('Research Management Detail Component', () => {
  let comp: ResearchDetailComponent;
  let fixture: ComponentFixture<ResearchDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResearchDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ research: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ResearchDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResearchDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load research on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.research).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});

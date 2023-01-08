import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IResearch } from '../research.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../research.test-samples';

import { ResearchService, RestResearch } from './research.service';

const requireRestSample: RestResearch = {
  ...sampleWithRequiredData,
  createdat: sampleWithRequiredData.createdat?.format(DATE_FORMAT),
  updatedat: sampleWithRequiredData.updatedat?.format(DATE_FORMAT),
};

describe('Research Service', () => {
  let service: ResearchService;
  let httpMock: HttpTestingController;
  let expectedResult: IResearch | IResearch[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ResearchService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Research', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const research = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(research).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Research', () => {
      const research = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(research).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Research', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Research', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Research', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addResearchToCollectionIfMissing', () => {
      it('should add a Research to an empty array', () => {
        const research: IResearch = sampleWithRequiredData;
        expectedResult = service.addResearchToCollectionIfMissing([], research);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(research);
      });

      it('should not add a Research to an array that contains it', () => {
        const research: IResearch = sampleWithRequiredData;
        const researchCollection: IResearch[] = [
          {
            ...research,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addResearchToCollectionIfMissing(researchCollection, research);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Research to an array that doesn't contain it", () => {
        const research: IResearch = sampleWithRequiredData;
        const researchCollection: IResearch[] = [sampleWithPartialData];
        expectedResult = service.addResearchToCollectionIfMissing(researchCollection, research);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(research);
      });

      it('should add only unique Research to an array', () => {
        const researchArray: IResearch[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const researchCollection: IResearch[] = [sampleWithRequiredData];
        expectedResult = service.addResearchToCollectionIfMissing(researchCollection, ...researchArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const research: IResearch = sampleWithRequiredData;
        const research2: IResearch = sampleWithPartialData;
        expectedResult = service.addResearchToCollectionIfMissing([], research, research2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(research);
        expect(expectedResult).toContain(research2);
      });

      it('should accept null and undefined values', () => {
        const research: IResearch = sampleWithRequiredData;
        expectedResult = service.addResearchToCollectionIfMissing([], null, research, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(research);
      });

      it('should return initial array if no Research is added', () => {
        const researchCollection: IResearch[] = [sampleWithRequiredData];
        expectedResult = service.addResearchToCollectionIfMissing(researchCollection, undefined, null);
        expect(expectedResult).toEqual(researchCollection);
      });
    });

    describe('compareResearch', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareResearch(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareResearch(entity1, entity2);
        const compareResult2 = service.compareResearch(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareResearch(entity1, entity2);
        const compareResult2 = service.compareResearch(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareResearch(entity1, entity2);
        const compareResult2 = service.compareResearch(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

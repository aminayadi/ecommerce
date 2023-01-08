import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPfield } from '../pfield.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../pfield.test-samples';

import { PfieldService } from './pfield.service';

const requireRestSample: IPfield = {
  ...sampleWithRequiredData,
};

describe('Pfield Service', () => {
  let service: PfieldService;
  let httpMock: HttpTestingController;
  let expectedResult: IPfield | IPfield[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PfieldService);
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

    it('should create a Pfield', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const pfield = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pfield).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Pfield', () => {
      const pfield = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pfield).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Pfield', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Pfield', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Pfield', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPfieldToCollectionIfMissing', () => {
      it('should add a Pfield to an empty array', () => {
        const pfield: IPfield = sampleWithRequiredData;
        expectedResult = service.addPfieldToCollectionIfMissing([], pfield);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pfield);
      });

      it('should not add a Pfield to an array that contains it', () => {
        const pfield: IPfield = sampleWithRequiredData;
        const pfieldCollection: IPfield[] = [
          {
            ...pfield,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPfieldToCollectionIfMissing(pfieldCollection, pfield);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Pfield to an array that doesn't contain it", () => {
        const pfield: IPfield = sampleWithRequiredData;
        const pfieldCollection: IPfield[] = [sampleWithPartialData];
        expectedResult = service.addPfieldToCollectionIfMissing(pfieldCollection, pfield);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pfield);
      });

      it('should add only unique Pfield to an array', () => {
        const pfieldArray: IPfield[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pfieldCollection: IPfield[] = [sampleWithRequiredData];
        expectedResult = service.addPfieldToCollectionIfMissing(pfieldCollection, ...pfieldArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pfield: IPfield = sampleWithRequiredData;
        const pfield2: IPfield = sampleWithPartialData;
        expectedResult = service.addPfieldToCollectionIfMissing([], pfield, pfield2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pfield);
        expect(expectedResult).toContain(pfield2);
      });

      it('should accept null and undefined values', () => {
        const pfield: IPfield = sampleWithRequiredData;
        expectedResult = service.addPfieldToCollectionIfMissing([], null, pfield, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pfield);
      });

      it('should return initial array if no Pfield is added', () => {
        const pfieldCollection: IPfield[] = [sampleWithRequiredData];
        expectedResult = service.addPfieldToCollectionIfMissing(pfieldCollection, undefined, null);
        expect(expectedResult).toEqual(pfieldCollection);
      });
    });

    describe('comparePfield', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePfield(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.comparePfield(entity1, entity2);
        const compareResult2 = service.comparePfield(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.comparePfield(entity1, entity2);
        const compareResult2 = service.comparePfield(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.comparePfield(entity1, entity2);
        const compareResult2 = service.comparePfield(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

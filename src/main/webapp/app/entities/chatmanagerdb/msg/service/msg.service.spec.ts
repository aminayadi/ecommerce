import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMsg } from '../msg.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../msg.test-samples';

import { MsgService } from './msg.service';

const requireRestSample: IMsg = {
  ...sampleWithRequiredData,
};

describe('Msg Service', () => {
  let service: MsgService;
  let httpMock: HttpTestingController;
  let expectedResult: IMsg | IMsg[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MsgService);
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

    it('should create a Msg', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const msg = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(msg).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Msg', () => {
      const msg = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(msg).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Msg', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Msg', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Msg', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMsgToCollectionIfMissing', () => {
      it('should add a Msg to an empty array', () => {
        const msg: IMsg = sampleWithRequiredData;
        expectedResult = service.addMsgToCollectionIfMissing([], msg);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(msg);
      });

      it('should not add a Msg to an array that contains it', () => {
        const msg: IMsg = sampleWithRequiredData;
        const msgCollection: IMsg[] = [
          {
            ...msg,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMsgToCollectionIfMissing(msgCollection, msg);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Msg to an array that doesn't contain it", () => {
        const msg: IMsg = sampleWithRequiredData;
        const msgCollection: IMsg[] = [sampleWithPartialData];
        expectedResult = service.addMsgToCollectionIfMissing(msgCollection, msg);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(msg);
      });

      it('should add only unique Msg to an array', () => {
        const msgArray: IMsg[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const msgCollection: IMsg[] = [sampleWithRequiredData];
        expectedResult = service.addMsgToCollectionIfMissing(msgCollection, ...msgArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const msg: IMsg = sampleWithRequiredData;
        const msg2: IMsg = sampleWithPartialData;
        expectedResult = service.addMsgToCollectionIfMissing([], msg, msg2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(msg);
        expect(expectedResult).toContain(msg2);
      });

      it('should accept null and undefined values', () => {
        const msg: IMsg = sampleWithRequiredData;
        expectedResult = service.addMsgToCollectionIfMissing([], null, msg, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(msg);
      });

      it('should return initial array if no Msg is added', () => {
        const msgCollection: IMsg[] = [sampleWithRequiredData];
        expectedResult = service.addMsgToCollectionIfMissing(msgCollection, undefined, null);
        expect(expectedResult).toEqual(msgCollection);
      });
    });

    describe('compareMsg', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMsg(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareMsg(entity1, entity2);
        const compareResult2 = service.compareMsg(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareMsg(entity1, entity2);
        const compareResult2 = service.compareMsg(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareMsg(entity1, entity2);
        const compareResult2 = service.compareMsg(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

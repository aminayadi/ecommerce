import dayjs from 'dayjs/esm';

import { IFavoris, NewFavoris } from './favoris.model';

export const sampleWithRequiredData: IFavoris = {
  id: 'e947f9f9-33a2-4d10-b68c-389322393a0a',
};

export const sampleWithPartialData: IFavoris = {
  id: '099b9e7e-3c4f-431f-a18d-023bed27fbd6',
  iduser: 'copying',
  deletedat: dayjs('2022-09-29'),
};

export const sampleWithFullData: IFavoris = {
  id: '72bfcec1-8903-430d-9d02-a41bfdd52574',
  idproduct: 'Gorgeous Cambridgeshire Chair',
  iduser: 'Fresh Tennessee',
  createdat: dayjs('2022-09-30'),
  modifiedat: dayjs('2022-09-30'),
  deletedat: dayjs('2022-09-30'),
};

export const sampleWithNewData: NewFavoris = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import dayjs from 'dayjs/esm';

import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 'c442dfc5-f1e3-4a23-b568-16f6b07dcadc',
};

export const sampleWithPartialData: IProduct = {
  id: '5732e9cd-2531-453f-a3a4-5b5084beccd5',
  idcategory: 'Tuna Marketing',
  iduser: 'Multi-lateral',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  zone: 'deposit Loaf withdrawal',
  createdat: dayjs('2022-09-29'),
};

export const sampleWithFullData: IProduct = {
  id: '8a63e85b-abd4-435b-83af-8bb9bbcfc4d9',
  idcategory: 'National',
  iduser: 'backing Iraqi',
  name: 'Legacy',
  photo: '../fake-data/blob/hipster.png',
  photoContentType: 'unknown',
  description: 'harness Clothing Fantastic',
  zone: 'Minnesota Movies Rubber',
  createdat: dayjs('2022-09-29'),
  updatedat: dayjs('2022-09-29'),
};

export const sampleWithNewData: NewProduct = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

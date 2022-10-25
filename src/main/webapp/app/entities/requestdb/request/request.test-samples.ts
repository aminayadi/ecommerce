import dayjs from 'dayjs/esm';

import { IRequest, NewRequest } from './request.model';

export const sampleWithRequiredData: IRequest = {
  id: 'e3f0b75f-4dcd-4a52-8038-3e8f26aa67b9',
};

export const sampleWithPartialData: IRequest = {
  id: '6f25c527-d8a7-457f-955a-c5b52c6fed42',
  iduser: 'Triple-buffered',
  idcategory: 'program',
  idproduct: 'Metal open-source',
  subject: 'Car Nevada 1080p',
  description: 'Concrete',
  modifiedat: dayjs('2022-09-30'),
  deletedat: dayjs('2022-09-30'),
};

export const sampleWithFullData: IRequest = {
  id: 'c7b5654b-a088-4090-8fe1-873123102456',
  iduser: 'SCSI system',
  idcategory: 'Customizable',
  idproduct: 'Borders',
  subject: 'Sleek Account Tobago',
  description: 'Honduras bypassing rich',
  createdat: dayjs('2022-09-29'),
  modifiedat: dayjs('2022-09-30'),
  deletedat: dayjs('2022-09-29'),
};

export const sampleWithNewData: NewRequest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

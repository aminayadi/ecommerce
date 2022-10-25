import dayjs from 'dayjs/esm';

import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: '0aaf2144-edc0-4adb-933d-efd5dc5e205d',
};

export const sampleWithPartialData: ICategory = {
  id: '1bfc1212-1b61-4d99-9b9e-680cf6dd7bb5',
  status: 45895,
  updatedat: dayjs('2022-09-28'),
  parent: 'solutions benchmark',
};

export const sampleWithFullData: ICategory = {
  id: '0140cf16-d432-4ddb-be9b-b54b41572efe',
  idparent: 74200,
  name: 'Metal',
  status: 48417,
  createdat: dayjs('2022-09-28'),
  updatedat: dayjs('2022-09-28'),
  parent: 'Communications',
};

export const sampleWithNewData: NewCategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

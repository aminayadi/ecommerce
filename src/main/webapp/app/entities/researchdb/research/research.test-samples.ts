import dayjs from 'dayjs/esm';

import { IResearch, NewResearch } from './research.model';

export const sampleWithRequiredData: IResearch = {
  id: '11bccef5-e070-4627-85d2-af4b40c518fc',
};

export const sampleWithPartialData: IResearch = {
  id: 'a0ef5c51-9d7d-4402-b676-179282e9d5d6',
  createdat: dayjs('2022-09-29'),
  zone: 'Rubber Account',
};

export const sampleWithFullData: IResearch = {
  id: 'a0f71f55-2335-4a1c-86ec-e2d6456b4209',
  iduser: 'Unbranded Specialist Gloves',
  idcategory: 'Dakota',
  idzone: 'hacking lime Islands',
  createdat: dayjs('2022-09-30'),
  updatedat: dayjs('2022-09-29'),
  zone: 'Ergonomic compressing',
};

export const sampleWithNewData: NewResearch = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

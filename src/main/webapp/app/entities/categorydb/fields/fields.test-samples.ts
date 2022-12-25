import { etype } from 'app/entities/enumerations/etype.model';

import { IFields, NewFields } from './fields.model';

export const sampleWithRequiredData: IFields = {
  id: '0622da08-62d3-4dcb-bb0b-e492d02d34f7',
  name: 'Market Steel',
  type: etype['NUMBER'],
};

export const sampleWithPartialData: IFields = {
  id: '33cbdf4b-850e-4a0f-a408-e32da67ac773',
  name: 'platforms',
  type: etype['DATE'],
};

export const sampleWithFullData: IFields = {
  id: '48cfd70b-67cf-4afa-a6e7-95e58833a096',
  name: 'Accounts Incredible facilitate',
  type: etype['DATE'],
};

export const sampleWithNewData: NewFields = {
  name: 'front-end Realigned Unbranded',
  type: etype['STRING'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

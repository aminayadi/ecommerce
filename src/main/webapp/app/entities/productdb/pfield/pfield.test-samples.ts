import { etype } from 'app/entities/enumerations/etype.model';

import { IPfield, NewPfield } from './pfield.model';

export const sampleWithRequiredData: IPfield = {
  id: 'b1ea1311-e0b9-470a-af84-a13e6dc0448d',
  name: 'programming',
  type: etype['NUMBER'],
};

export const sampleWithPartialData: IPfield = {
  id: '632bf067-e13d-4e50-89ec-0a81364f6b0f',
  name: 'Agent Gloves New',
  type: etype['STRING'],
  value: 'definition Computer',
};

export const sampleWithFullData: IPfield = {
  id: '4cd7aa74-4cf0-4695-8f91-b651a9dd7cf0',
  name: 'Guarani',
  type: etype['NUMBER'],
  value: 'Account compelling leading-edge',
};

export const sampleWithNewData: NewPfield = {
  name: 'platforms',
  type: etype['DATE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

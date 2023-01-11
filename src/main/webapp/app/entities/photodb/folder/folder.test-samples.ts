import { IFolder, NewFolder } from './folder.model';

export const sampleWithRequiredData: IFolder = {
  id: '477c855b-a5e9-47da-abbb-23cc7b3dd73f',
  name: '24/7 payment matrix',
};

export const sampleWithPartialData: IFolder = {
  id: '0f374b72-a815-460c-8bb6-150a895af40c',
  name: 'sexy',
};

export const sampleWithFullData: IFolder = {
  id: '27842dc8-ce05-4270-85f7-14bed67caad9',
  name: 'Fantastic reinvent',
};

export const sampleWithNewData: NewFolder = {
  name: 'Bacon',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

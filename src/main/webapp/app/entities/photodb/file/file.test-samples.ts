import { IFile, NewFile } from './file.model';

export const sampleWithRequiredData: IFile = {
  id: '52681666-b58c-4fb5-9832-b4849d67dae9',
  name: 'redundant aggregate',
};

export const sampleWithPartialData: IFile = {
  id: 'eed17fac-075f-4999-82f4-490626d6034f',
  name: 'Louisiana',
  type: 'tan Delaware',
};

export const sampleWithFullData: IFile = {
  id: '7ee3f242-3c92-45be-9ceb-5827bf1f36f3',
  name: 'content-based',
  type: 'Avon',
};

export const sampleWithNewData: NewFile = {
  name: 'Chief Advanced generating',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

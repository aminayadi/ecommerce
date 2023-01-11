import { IPhoto, NewPhoto } from './photo.model';

export const sampleWithRequiredData: IPhoto = {
  id: '635759be-939e-41d9-8789-6fe041e739c6',
  path: 'Ergonomic orchestration',
};

export const sampleWithPartialData: IPhoto = {
  id: '932721c0-0e89-4696-8ae6-9d1c2795deac',
  path: 'Ball attitude',
};

export const sampleWithFullData: IPhoto = {
  id: '9c28aa6f-3a13-44c6-ae27-c1ff42002208',
  path: 'withdrawal Incredible copying',
  type: 'back panel',
};

export const sampleWithNewData: NewPhoto = {
  path: 'Forward Riel',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

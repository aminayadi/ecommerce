import { IMsg, NewMsg } from './msg.model';

export const sampleWithRequiredData: IMsg = {
  id: 'e244aa4c-77d7-4d4c-beaa-90bdbf7a2cae',
  from: 'Identity Cambridgeshire',
  fromUserName: 'enable Concrete',
  message: 'Avon Ergonomic',
};

export const sampleWithPartialData: IMsg = {
  id: '4b929b1a-0c64-418b-91ba-94474072e964',
  from: 'Chief Hat matrices',
  fromUserName: 'Inverse green Ball',
  message: 'deposit Dollar',
};

export const sampleWithFullData: IMsg = {
  id: '9b543d93-d734-4935-8752-bf59aa06dd2b',
  type: 'RSS Developer leverage',
  from: 'plum Tuna',
  fromUserName: 'redundant',
  message: 'utilisation Executive',
};

export const sampleWithNewData: NewMsg = {
  from: 'Pound intuitive',
  fromUserName: 'Wooden',
  message: 'turquoise Plastic Avon',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

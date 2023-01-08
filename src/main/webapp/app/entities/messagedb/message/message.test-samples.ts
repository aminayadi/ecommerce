import dayjs from 'dayjs/esm';

import { IMessage, NewMessage } from './message.model';

export const sampleWithRequiredData: IMessage = {
  id: '4f5a3d40-9bf6-4ec2-ae8d-95e9265434c5',
};

export const sampleWithPartialData: IMessage = {
  id: '859372c5-be12-46e3-9af7-7f10bdfc9ee8',
  iduser: 'payment Facilitator',
  idproduct: 'plug-and-play',
  idsender: 'Handcrafted array GB',
  idreceiver: 'copying Dollar Operations',
  subject: 'COM Buckinghamshire',
  hiddenat: dayjs('2022-09-29'),
};

export const sampleWithFullData: IMessage = {
  id: '1648fc3b-e975-414f-81ba-fc98520e729e',
  iduser: 'Beauty matrix',
  idproduct: 'Oklahoma',
  idsender: 'cyan',
  idreceiver: 'killer',
  subject: 'programming Lead',
  description: 'Ball Maryland transmitting',
  createdat: dayjs('2022-09-29'),
  hiddenat: dayjs('2022-09-29'),
};

export const sampleWithNewData: NewMessage = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

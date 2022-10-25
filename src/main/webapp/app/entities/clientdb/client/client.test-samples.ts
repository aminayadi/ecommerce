import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 'b5d26ec1-78ed-413c-a418-9dee47137427',
};

export const sampleWithPartialData: IClient = {
  id: '403cc418-0b6c-463f-9c75-91621bc0e726',
  fname: 'Soft',
  lname: 'definition Synergized Brand',
  email: 'Micheal_Bashirian50@yahoo.com',
  phone: '1-932-841-7852 x5296',
  type: 86801,
};

export const sampleWithFullData: IClient = {
  id: '32ffde4b-2213-4279-8336-92f042aad503',
  fname: 'Supervisor Sierra',
  lname: 'Distributed',
  email: 'Coralie_Rohan89@hotmail.com',
  phone: '1-572-987-3666 x74074',
  type: 50654,
};

export const sampleWithNewData: NewClient = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

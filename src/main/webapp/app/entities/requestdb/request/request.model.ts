import dayjs from 'dayjs/esm';

export interface IRequest {
  id: string;
  iduser?: string | null;
  idcategory?: string | null;
  idproduct?: string | null;
  subject?: string | null;
  description?: string | null;
  createdat?: dayjs.Dayjs | null;
  modifiedat?: dayjs.Dayjs | null;
  deletedat?: dayjs.Dayjs | null;
}

export type NewRequest = Omit<IRequest, 'id'> & { id: null };

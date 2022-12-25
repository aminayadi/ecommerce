import dayjs from 'dayjs/esm';

export interface IFavoris {
  id: string;
  idproduct?: string | null;
  iduser?: string | null;
  createdat?: dayjs.Dayjs | null;
  modifiedat?: dayjs.Dayjs | null;
  deletedat?: dayjs.Dayjs | null;
}

export type NewFavoris = Omit<IFavoris, 'id'> & { id: null };

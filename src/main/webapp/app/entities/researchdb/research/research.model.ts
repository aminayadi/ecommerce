import dayjs from 'dayjs/esm';

export interface IResearch {
  id: string;
  iduser?: string | null;
  idcategory?: string | null;
  idzone?: string | null;
  createdat?: dayjs.Dayjs | null;
  updatedat?: dayjs.Dayjs | null;
  zone?: string | null;
}

export type NewResearch = Omit<IResearch, 'id'> & { id: null };

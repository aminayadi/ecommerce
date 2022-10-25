import dayjs from 'dayjs/esm';

export interface ICategory {
  id: string;
  idparent?: number | null;
  name?: string | null;
  status?: number | null;
  createdat?: dayjs.Dayjs | null;
  updatedat?: dayjs.Dayjs | null;
  parent?: string | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };

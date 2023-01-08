import dayjs from 'dayjs/esm';

export interface IProduct {
  id: string;
  idcategory?: string | null;
  iduser?: string | null;
  name?: string | null;
  photo?: string | null;
  photoContentType?: string | null;
  description?: string | null;
  zone?: string | null;
  createdat?: dayjs.Dayjs | null;
  updatedat?: dayjs.Dayjs | null;
}

export type NewProduct = Omit<IProduct, 'id'> & { id: null };

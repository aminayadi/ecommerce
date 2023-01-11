import { IProduct } from 'app/entities/productdb/product/product.model';

export interface IPhoto {
  id: string;
  path?: string | null;
  type?: string | null;
  product?: Pick<IProduct, 'id'> | null;
}

export type NewPhoto = Omit<IPhoto, 'id'> & { id: null };

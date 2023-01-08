import { IProduct } from 'app/entities/productdb/product/product.model';
import { etype } from 'app/entities/enumerations/etype.model';

export interface IPfield {
  id: string;
  name?: string | null;
  type?: etype | null;
  value?: string | null;
  product?: Pick<IProduct, 'id'> | null;
}

export type NewPfield = Omit<IPfield, 'id'> & { id: null };

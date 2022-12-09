import { ICategory } from 'app/entities/categorydb/category/category.model';
import { etype } from 'app/entities/enumerations/etype.model';

export interface IFields {
  id: string;
  name?: string | null;
  type?: etype | null;
  category?: Pick<ICategory, 'id' | 'name'> | null;
}

export type NewFields = Omit<IFields, 'id'> & { id: null };

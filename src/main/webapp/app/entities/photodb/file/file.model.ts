import { IFolder } from 'app/entities/photodb/folder/folder.model';

export interface IFile {
  id: string;
  name?: string | null;
  type?: string | null;
  folder?: Pick<IFolder, 'id'> | null;
}

export type NewFile = Omit<IFile, 'id'> & { id: null };

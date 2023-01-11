export interface IFolder {
  id: string;
  name?: string | null;
}

export type NewFolder = Omit<IFolder, 'id'> & { id: null };

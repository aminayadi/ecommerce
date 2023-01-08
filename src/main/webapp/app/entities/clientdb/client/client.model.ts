export interface IClient {
  id: string;
  fname?: string | null;
  lname?: string | null;
  email?: string | null;
  phone?: string | null;
  type?: number | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };

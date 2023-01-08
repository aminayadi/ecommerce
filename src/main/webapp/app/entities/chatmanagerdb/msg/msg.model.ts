export interface IMsg {
  id: string;
  type?: string | null;
  from?: string | null;
  fromUserName?: string | null;
  message?: string | null;
}

export type NewMsg = Omit<IMsg, 'id'> & { id: null };

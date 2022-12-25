import dayjs from 'dayjs/esm';

export interface IMessage {
  id: string;
  iduser?: string | null;
  idproduct?: string | null;
  idsender?: string | null;
  idreceiver?: string | null;
  subject?: string | null;
  description?: string | null;
  createdat?: dayjs.Dayjs | null;
  hiddenat?: dayjs.Dayjs | null;
}

export type NewMessage = Omit<IMessage, 'id'> & { id: null };

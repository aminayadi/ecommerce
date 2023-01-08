import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMsg, NewMsg } from '../msg.model';

export type PartialUpdateMsg = Partial<IMsg> & Pick<IMsg, 'id'>;

export type EntityResponseType = HttpResponse<IMsg>;
export type EntityArrayResponseType = HttpResponse<IMsg[]>;

@Injectable({ providedIn: 'root' })
export class MsgService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/msgs', 'chatmanagerdb');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(msg: NewMsg): Observable<EntityResponseType> {
    return this.http.post<IMsg>(this.resourceUrl, msg, { observe: 'response' });
  }

  update(msg: IMsg): Observable<EntityResponseType> {
    return this.http.put<IMsg>(`${this.resourceUrl}/${this.getMsgIdentifier(msg)}`, msg, { observe: 'response' });
  }

  partialUpdate(msg: PartialUpdateMsg): Observable<EntityResponseType> {
    return this.http.patch<IMsg>(`${this.resourceUrl}/${this.getMsgIdentifier(msg)}`, msg, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IMsg>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMsg[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMsgIdentifier(msg: Pick<IMsg, 'id'>): string {
    return msg.id;
  }

  compareMsg(o1: Pick<IMsg, 'id'> | null, o2: Pick<IMsg, 'id'> | null): boolean {
    return o1 && o2 ? this.getMsgIdentifier(o1) === this.getMsgIdentifier(o2) : o1 === o2;
  }

  addMsgToCollectionIfMissing<Type extends Pick<IMsg, 'id'>>(msgCollection: Type[], ...msgsToCheck: (Type | null | undefined)[]): Type[] {
    const msgs: Type[] = msgsToCheck.filter(isPresent);
    if (msgs.length > 0) {
      const msgCollectionIdentifiers = msgCollection.map(msgItem => this.getMsgIdentifier(msgItem)!);
      const msgsToAdd = msgs.filter(msgItem => {
        const msgIdentifier = this.getMsgIdentifier(msgItem);
        if (msgCollectionIdentifiers.includes(msgIdentifier)) {
          return false;
        }
        msgCollectionIdentifiers.push(msgIdentifier);
        return true;
      });
      return [...msgsToAdd, ...msgCollection];
    }
    return msgCollection;
  }
}

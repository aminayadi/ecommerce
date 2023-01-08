import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPfield, NewPfield } from '../pfield.model';

export type PartialUpdatePfield = Partial<IPfield> & Pick<IPfield, 'id'>;

export type EntityResponseType = HttpResponse<IPfield>;
export type EntityArrayResponseType = HttpResponse<IPfield[]>;

@Injectable({ providedIn: 'root' })
export class PfieldService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pfields', 'productdb');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pfield: NewPfield): Observable<EntityResponseType> {
    return this.http.post<IPfield>(this.resourceUrl, pfield, { observe: 'response' });
  }

  update(pfield: IPfield): Observable<EntityResponseType> {
    return this.http.put<IPfield>(`${this.resourceUrl}/${this.getPfieldIdentifier(pfield)}`, pfield, { observe: 'response' });
  }

  partialUpdate(pfield: PartialUpdatePfield): Observable<EntityResponseType> {
    return this.http.patch<IPfield>(`${this.resourceUrl}/${this.getPfieldIdentifier(pfield)}`, pfield, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPfield>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPfield[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPfieldIdentifier(pfield: Pick<IPfield, 'id'>): string {
    return pfield.id;
  }

  comparePfield(o1: Pick<IPfield, 'id'> | null, o2: Pick<IPfield, 'id'> | null): boolean {
    return o1 && o2 ? this.getPfieldIdentifier(o1) === this.getPfieldIdentifier(o2) : o1 === o2;
  }

  addPfieldToCollectionIfMissing<Type extends Pick<IPfield, 'id'>>(
    pfieldCollection: Type[],
    ...pfieldsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pfields: Type[] = pfieldsToCheck.filter(isPresent);
    if (pfields.length > 0) {
      const pfieldCollectionIdentifiers = pfieldCollection.map(pfieldItem => this.getPfieldIdentifier(pfieldItem)!);
      const pfieldsToAdd = pfields.filter(pfieldItem => {
        const pfieldIdentifier = this.getPfieldIdentifier(pfieldItem);
        if (pfieldCollectionIdentifiers.includes(pfieldIdentifier)) {
          return false;
        }
        pfieldCollectionIdentifiers.push(pfieldIdentifier);
        return true;
      });
      return [...pfieldsToAdd, ...pfieldCollection];
    }
    return pfieldCollection;
  }
}

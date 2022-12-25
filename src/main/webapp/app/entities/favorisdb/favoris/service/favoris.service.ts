import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFavoris, NewFavoris } from '../favoris.model';

export type PartialUpdateFavoris = Partial<IFavoris> & Pick<IFavoris, 'id'>;

type RestOf<T extends IFavoris | NewFavoris> = Omit<T, 'createdat' | 'modifiedat' | 'deletedat'> & {
  createdat?: string | null;
  modifiedat?: string | null;
  deletedat?: string | null;
};

export type RestFavoris = RestOf<IFavoris>;

export type NewRestFavoris = RestOf<NewFavoris>;

export type PartialUpdateRestFavoris = RestOf<PartialUpdateFavoris>;

export type EntityResponseType = HttpResponse<IFavoris>;
export type EntityArrayResponseType = HttpResponse<IFavoris[]>;

@Injectable({ providedIn: 'root' })
export class FavorisService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/favorises', 'favorisdb');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(favoris: NewFavoris): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(favoris);
    return this.http
      .post<RestFavoris>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(favoris: IFavoris): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(favoris);
    return this.http
      .put<RestFavoris>(`${this.resourceUrl}/${this.getFavorisIdentifier(favoris)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(favoris: PartialUpdateFavoris): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(favoris);
    return this.http
      .patch<RestFavoris>(`${this.resourceUrl}/${this.getFavorisIdentifier(favoris)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestFavoris>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFavoris[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFavorisIdentifier(favoris: Pick<IFavoris, 'id'>): string {
    return favoris.id;
  }

  compareFavoris(o1: Pick<IFavoris, 'id'> | null, o2: Pick<IFavoris, 'id'> | null): boolean {
    return o1 && o2 ? this.getFavorisIdentifier(o1) === this.getFavorisIdentifier(o2) : o1 === o2;
  }

  addFavorisToCollectionIfMissing<Type extends Pick<IFavoris, 'id'>>(
    favorisCollection: Type[],
    ...favorisesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const favorises: Type[] = favorisesToCheck.filter(isPresent);
    if (favorises.length > 0) {
      const favorisCollectionIdentifiers = favorisCollection.map(favorisItem => this.getFavorisIdentifier(favorisItem)!);
      const favorisesToAdd = favorises.filter(favorisItem => {
        const favorisIdentifier = this.getFavorisIdentifier(favorisItem);
        if (favorisCollectionIdentifiers.includes(favorisIdentifier)) {
          return false;
        }
        favorisCollectionIdentifiers.push(favorisIdentifier);
        return true;
      });
      return [...favorisesToAdd, ...favorisCollection];
    }
    return favorisCollection;
  }

  protected convertDateFromClient<T extends IFavoris | NewFavoris | PartialUpdateFavoris>(favoris: T): RestOf<T> {
    return {
      ...favoris,
      createdat: favoris.createdat?.format(DATE_FORMAT) ?? null,
      modifiedat: favoris.modifiedat?.format(DATE_FORMAT) ?? null,
      deletedat: favoris.deletedat?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFavoris: RestFavoris): IFavoris {
    return {
      ...restFavoris,
      createdat: restFavoris.createdat ? dayjs(restFavoris.createdat) : undefined,
      modifiedat: restFavoris.modifiedat ? dayjs(restFavoris.modifiedat) : undefined,
      deletedat: restFavoris.deletedat ? dayjs(restFavoris.deletedat) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFavoris>): HttpResponse<IFavoris> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFavoris[]>): HttpResponse<IFavoris[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

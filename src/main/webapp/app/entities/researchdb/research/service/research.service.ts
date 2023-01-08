import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IResearch, NewResearch } from '../research.model';

export type PartialUpdateResearch = Partial<IResearch> & Pick<IResearch, 'id'>;

type RestOf<T extends IResearch | NewResearch> = Omit<T, 'createdat' | 'updatedat'> & {
  createdat?: string | null;
  updatedat?: string | null;
};

export type RestResearch = RestOf<IResearch>;

export type NewRestResearch = RestOf<NewResearch>;

export type PartialUpdateRestResearch = RestOf<PartialUpdateResearch>;

export type EntityResponseType = HttpResponse<IResearch>;
export type EntityArrayResponseType = HttpResponse<IResearch[]>;

@Injectable({ providedIn: 'root' })
export class ResearchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/research', 'researchdb');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(research: NewResearch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(research);
    return this.http
      .post<RestResearch>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(research: IResearch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(research);
    return this.http
      .put<RestResearch>(`${this.resourceUrl}/${this.getResearchIdentifier(research)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(research: PartialUpdateResearch): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(research);
    return this.http
      .patch<RestResearch>(`${this.resourceUrl}/${this.getResearchIdentifier(research)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestResearch>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestResearch[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getResearchIdentifier(research: Pick<IResearch, 'id'>): string {
    return research.id;
  }

  compareResearch(o1: Pick<IResearch, 'id'> | null, o2: Pick<IResearch, 'id'> | null): boolean {
    return o1 && o2 ? this.getResearchIdentifier(o1) === this.getResearchIdentifier(o2) : o1 === o2;
  }

  addResearchToCollectionIfMissing<Type extends Pick<IResearch, 'id'>>(
    researchCollection: Type[],
    ...researchToCheck: (Type | null | undefined)[]
  ): Type[] {
    const research: Type[] = researchToCheck.filter(isPresent);
    if (research.length > 0) {
      const researchCollectionIdentifiers = researchCollection.map(researchItem => this.getResearchIdentifier(researchItem)!);
      const researchToAdd = research.filter(researchItem => {
        const researchIdentifier = this.getResearchIdentifier(researchItem);
        if (researchCollectionIdentifiers.includes(researchIdentifier)) {
          return false;
        }
        researchCollectionIdentifiers.push(researchIdentifier);
        return true;
      });
      return [...researchToAdd, ...researchCollection];
    }
    return researchCollection;
  }

  protected convertDateFromClient<T extends IResearch | NewResearch | PartialUpdateResearch>(research: T): RestOf<T> {
    return {
      ...research,
      createdat: research.createdat?.format(DATE_FORMAT) ?? null,
      updatedat: research.updatedat?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restResearch: RestResearch): IResearch {
    return {
      ...restResearch,
      createdat: restResearch.createdat ? dayjs(restResearch.createdat) : undefined,
      updatedat: restResearch.updatedat ? dayjs(restResearch.updatedat) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestResearch>): HttpResponse<IResearch> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestResearch[]>): HttpResponse<IResearch[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

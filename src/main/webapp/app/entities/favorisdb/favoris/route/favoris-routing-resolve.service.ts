import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFavoris } from '../favoris.model';
import { FavorisService } from '../service/favoris.service';

@Injectable({ providedIn: 'root' })
export class FavorisRoutingResolveService implements Resolve<IFavoris | null> {
  constructor(protected service: FavorisService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFavoris | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((favoris: HttpResponse<IFavoris>) => {
          if (favoris.body) {
            return of(favoris.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}

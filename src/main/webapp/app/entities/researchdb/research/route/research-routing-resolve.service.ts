import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResearch } from '../research.model';
import { ResearchService } from '../service/research.service';

@Injectable({ providedIn: 'root' })
export class ResearchRoutingResolveService implements Resolve<IResearch | null> {
  constructor(protected service: ResearchService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResearch | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((research: HttpResponse<IResearch>) => {
          if (research.body) {
            return of(research.body);
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

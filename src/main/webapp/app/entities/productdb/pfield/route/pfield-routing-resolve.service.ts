import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPfield } from '../pfield.model';
import { PfieldService } from '../service/pfield.service';

@Injectable({ providedIn: 'root' })
export class PfieldRoutingResolveService implements Resolve<IPfield | null> {
  constructor(protected service: PfieldService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPfield | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pfield: HttpResponse<IPfield>) => {
          if (pfield.body) {
            return of(pfield.body);
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

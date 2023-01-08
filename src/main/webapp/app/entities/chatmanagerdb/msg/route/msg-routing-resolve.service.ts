import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMsg } from '../msg.model';
import { MsgService } from '../service/msg.service';

@Injectable({ providedIn: 'root' })
export class MsgRoutingResolveService implements Resolve<IMsg | null> {
  constructor(protected service: MsgService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMsg | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((msg: HttpResponse<IMsg>) => {
          if (msg.body) {
            return of(msg.body);
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

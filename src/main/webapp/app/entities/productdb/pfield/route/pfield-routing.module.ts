import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PfieldComponent } from '../list/pfield.component';
import { PfieldDetailComponent } from '../detail/pfield-detail.component';
import { PfieldUpdateComponent } from '../update/pfield-update.component';
import { PfieldRoutingResolveService } from './pfield-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const pfieldRoute: Routes = [
  {
    path: '',
    component: PfieldComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PfieldDetailComponent,
    resolve: {
      pfield: PfieldRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PfieldUpdateComponent,
    resolve: {
      pfield: PfieldRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PfieldUpdateComponent,
    resolve: {
      pfield: PfieldRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pfieldRoute)],
  exports: [RouterModule],
})
export class PfieldRoutingModule {}

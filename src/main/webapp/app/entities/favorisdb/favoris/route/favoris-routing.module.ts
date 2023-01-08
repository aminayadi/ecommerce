import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FavorisComponent } from '../list/favoris.component';
import { FavorisDetailComponent } from '../detail/favoris-detail.component';
import { FavorisUpdateComponent } from '../update/favoris-update.component';
import { FavorisRoutingResolveService } from './favoris-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const favorisRoute: Routes = [
  {
    path: '',
    component: FavorisComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FavorisDetailComponent,
    resolve: {
      favoris: FavorisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FavorisUpdateComponent,
    resolve: {
      favoris: FavorisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FavorisUpdateComponent,
    resolve: {
      favoris: FavorisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(favorisRoute)],
  exports: [RouterModule],
})
export class FavorisRoutingModule {}

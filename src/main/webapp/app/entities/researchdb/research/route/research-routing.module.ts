import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResearchComponent } from '../list/research.component';
import { ResearchDetailComponent } from '../detail/research-detail.component';
import { ResearchUpdateComponent } from '../update/research-update.component';
import { ResearchRoutingResolveService } from './research-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const researchRoute: Routes = [
  {
    path: '',
    component: ResearchComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResearchDetailComponent,
    resolve: {
      research: ResearchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResearchUpdateComponent,
    resolve: {
      research: ResearchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResearchUpdateComponent,
    resolve: {
      research: ResearchRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(researchRoute)],
  exports: [RouterModule],
})
export class ResearchRoutingModule {}

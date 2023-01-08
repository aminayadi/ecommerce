import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MsgComponent } from '../list/msg.component';
import { MsgDetailComponent } from '../detail/msg-detail.component';
import { MsgUpdateComponent } from '../update/msg-update.component';
import { MsgRoutingResolveService } from './msg-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const msgRoute: Routes = [
  {
    path: '',
    component: MsgComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MsgDetailComponent,
    resolve: {
      msg: MsgRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MsgUpdateComponent,
    resolve: {
      msg: MsgRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MsgUpdateComponent,
    resolve: {
      msg: MsgRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(msgRoute)],
  exports: [RouterModule],
})
export class MsgRoutingModule {}

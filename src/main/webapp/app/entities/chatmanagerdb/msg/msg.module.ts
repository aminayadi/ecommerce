import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MsgComponent } from './list/msg.component';
import { MsgDetailComponent } from './detail/msg-detail.component';
import { MsgUpdateComponent } from './update/msg-update.component';
import { MsgDeleteDialogComponent } from './delete/msg-delete-dialog.component';
import { MsgRoutingModule } from './route/msg-routing.module';

@NgModule({
  imports: [SharedModule, MsgRoutingModule],
  declarations: [MsgComponent, MsgDetailComponent, MsgUpdateComponent, MsgDeleteDialogComponent],
})
export class ChatmanagerdbMsgModule {}

import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PfieldComponent } from './list/pfield.component';
import { PfieldDetailComponent } from './detail/pfield-detail.component';
import { PfieldUpdateComponent } from './update/pfield-update.component';
import { PfieldDeleteDialogComponent } from './delete/pfield-delete-dialog.component';
import { PfieldRoutingModule } from './route/pfield-routing.module';

@NgModule({
  imports: [SharedModule, PfieldRoutingModule],
  declarations: [PfieldComponent, PfieldDetailComponent, PfieldUpdateComponent, PfieldDeleteDialogComponent],
})
export class ProductdbPfieldModule {}

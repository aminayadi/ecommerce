import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FavorisComponent } from './list/favoris.component';
import { FavorisDetailComponent } from './detail/favoris-detail.component';
import { FavorisUpdateComponent } from './update/favoris-update.component';
import { FavorisDeleteDialogComponent } from './delete/favoris-delete-dialog.component';
import { FavorisRoutingModule } from './route/favoris-routing.module';

@NgModule({
  imports: [SharedModule, FavorisRoutingModule],
  declarations: [FavorisComponent, FavorisDetailComponent, FavorisUpdateComponent, FavorisDeleteDialogComponent],
})
export class FavorisdbFavorisModule {}

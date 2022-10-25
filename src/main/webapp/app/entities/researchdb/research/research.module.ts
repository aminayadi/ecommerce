import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResearchComponent } from './list/research.component';
import { ResearchDetailComponent } from './detail/research-detail.component';
import { ResearchUpdateComponent } from './update/research-update.component';
import { ResearchDeleteDialogComponent } from './delete/research-delete-dialog.component';
import { ResearchRoutingModule } from './route/research-routing.module';

@NgModule({
  imports: [SharedModule, ResearchRoutingModule],
  declarations: [ResearchComponent, ResearchDetailComponent, ResearchUpdateComponent, ResearchDeleteDialogComponent],
})
export class ResearchdbResearchModule {}

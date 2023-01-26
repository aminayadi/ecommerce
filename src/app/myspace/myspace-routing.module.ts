import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './about/about.component';
import { HelpComponent } from './help/help.component';
import { MyannouncesComponent } from './myannounces/myannounces.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
  {
    path: 'myspace/home',
    component: MyannouncesComponent,
  },
  {
    path: 'myspace/profile',
    component: ProfileComponent,
  },
  {
    path: 'myspace/about',
    component: AboutComponent,
  },
  {
    path: 'myspace/help',
    component: HelpComponent,
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyspaceRoutingModule { }

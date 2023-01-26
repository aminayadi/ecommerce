import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './components/account/account.component';
import { ClientComponent } from './components/client/client.component';
import { HomeComponent } from './home/home.component';
import { MyspaceComponent } from './myspace/myspace.component';
import { AboutComponent } from './pages/about/about.component';
import { HelpComponent } from './pages/help/help.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { ProfileComponent } from './pages/profile/profile.component';

const routes: Routes = [
  {path:'',component:HomeComponent},
  {path:'auth',component:AccountComponent},
  {path:'client',component:ClientComponent},
  {path:'admin/category',redirectTo: 'category/home'},
  {path:'myspace', component:MyspaceComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

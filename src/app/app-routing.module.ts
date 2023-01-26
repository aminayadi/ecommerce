import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './components/account/account.component';
import { ClientComponent } from './components/client/client.component';
import { HomeComponent } from './home/home.component';
import { MyspaceComponent } from './myspace/myspace.component';

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

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../home/home.component';
import { CreateComponent } from './create/create.component';

const routes: Routes = [
  {
    path: 'category/home',
    component: HomeComponent
  },
  {
    path: 'category/create',
    component: CreateComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoryRoutingModule { }

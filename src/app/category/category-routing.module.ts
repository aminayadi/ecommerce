import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../home/home.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { CreateComponent } from './create/create.component';
import { EditComponent } from './edit/edit.component';
import { FieldListComponent } from './field-list/field-list.component';


const routes: Routes = [
  
  {
    path: 'edit/:id',
    component: EditComponent
  },
  
  {
    path: 'category/create',
    component: CreateComponent
  },

  {
    path: 'category/list',
    component: CategoryListComponent
  },
  {
    path: 'fields/:id',
    component: FieldListComponent
  },
 
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoryRoutingModule { }

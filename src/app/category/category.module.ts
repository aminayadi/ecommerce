import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CategoryRoutingModule } from './category-routing.module';
import { HomeComponent } from './home/home.component';
import { FieldComponent } from './field/field.component';
import { FieldListComponent } from './field-list/field-list.component';
import { CreateComponent } from './create/create.component';


@NgModule({
  declarations: [
    HomeComponent,
    FieldComponent,
    FieldListComponent,
    CreateComponent
  ],
  imports: [
    CommonModule,
    CategoryRoutingModule
  ]
})
export class CategoryModule { }

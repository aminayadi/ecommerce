import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CategoryRoutingModule } from './category-routing.module';

import { FieldComponent } from './field/field.component';
import { FieldListComponent } from './field-list/field-list.component';
import { CreateComponent } from './create/create.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from '../components/header/header.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { EditComponent } from './edit/edit.component';
import { FieldsModule } from '../fields/fields.module';
@NgModule({
  declarations: [
    
    FieldComponent,
    FieldListComponent,
    CreateComponent,
    CategoryListComponent,
    EditComponent
  ],
  imports: [
    CommonModule,
    CategoryRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    FieldsModule
  ]
})
export class CategoryModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductRoutingModule } from './product-routing.module';
import { CreateComponent } from './create/create.component';
import { EditComponent } from './edit/edit.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PfieldComponent } from './pfield/pfield.component';
import { UploadImagesComponent } from './upload-images/upload-images.component';
import { ListComponent } from './list/list.component';



@NgModule({
    declarations: [
        CreateComponent,
        EditComponent,
        PfieldComponent,
        UploadImagesComponent,
        ListComponent
    ],
    imports: [
        CommonModule,
        ProductRoutingModule,
        FormsModule,
        ReactiveFormsModule,
     
    ],
    exports:[
      CreateComponent,
      ListComponent
    ]
})
export class ProductModule { }

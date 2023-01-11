import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductRoutingModule } from './product-routing.module';
import { HomeComponent } from './home/home.component';
import { CreateComponent } from './create/create.component';
import { EditComponent } from './edit/edit.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PfieldComponent } from './pfield/pfield.component';
import { UploadImagesComponent } from './upload-images/upload-images.component';



@NgModule({
    declarations: [
        HomeComponent,
        CreateComponent,
        EditComponent,
        PfieldComponent,
        UploadImagesComponent
    ],
    imports: [
        CommonModule,
        ProductRoutingModule,
        FormsModule,
        ReactiveFormsModule,
     
    ],
    exports:[
      CreateComponent
    ]
})
export class ProductModule { }

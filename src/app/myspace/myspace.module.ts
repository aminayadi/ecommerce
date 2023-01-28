import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyspaceRoutingModule } from './myspace-routing.module';
import { HelpComponent } from './help/help.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ProfileComponent } from './profile/profile.component';
import { BrowserModule } from '@angular/platform-browser';
import { AboutComponent } from './about/about.component';
import { MyannouncesComponent } from './myannounces/myannounces.component';
import { ListComponent } from '../product/list/list.component';
import { ProductModule } from '../product/product.module';
import { MyspaceComponent } from './myspace.component';
import { EditannounceComponent } from './editannounce/editannounce.component';

@NgModule({
  declarations: [
    AboutComponent,
    HelpComponent,
    MyannouncesComponent,
    NotFoundComponent,
    ProfileComponent,
    EditannounceComponent,
  ],
  imports: [
    CommonModule,
    MyspaceRoutingModule,
    BrowserModule,
    ProductModule
  ],
  exports: [
    AboutComponent,
    HelpComponent,
    MyannouncesComponent,
    NotFoundComponent,
    ProfileComponent,
    EditannounceComponent,

  ],
  bootstrap: [],
})
export class MyspaceModule { }

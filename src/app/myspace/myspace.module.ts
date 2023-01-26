import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyspaceRoutingModule } from './myspace-routing.module';

import { HelpComponent } from './help/help.component';

import { NotFoundComponent } from './not-found/not-found.component';
import { ProfileComponent } from './profile/profile.component';


import { BrowserModule } from '@angular/platform-browser';
import { AboutComponent } from './about/about.component';
import { MyannouncesComponent } from './myannounces/myannounces.component';



@NgModule({
  declarations: [
    AboutComponent,
    HelpComponent,
    MyannouncesComponent,
    NotFoundComponent,
    ProfileComponent,
  ],
  imports: [
    CommonModule,
    MyspaceRoutingModule,
    BrowserModule,

  ],
  exports: [
    AboutComponent,
    HelpComponent,
    MyannouncesComponent,
    NotFoundComponent,
    ProfileComponent,

  ],
  bootstrap: [],
})
export class MyspaceModule { }

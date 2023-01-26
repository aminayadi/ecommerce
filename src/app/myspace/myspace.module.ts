import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyspaceRoutingModule } from './myspace-routing.module';

import { HelpComponent } from './help/help.component';
import { HomeComponent } from './home/home.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ProfileComponent } from './profile/profile.component';
import { MyspaceComponent } from './myspace.component';


import { BrowserModule } from '@angular/platform-browser';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { AboutComponent } from './about/about.component';


@NgModule({
  declarations: [
    AboutComponent,
    HelpComponent,
    HomeComponent,
    NotFoundComponent,
    ProfileComponent,
  ],
  imports: [
    CommonModule,
    MyspaceRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
  ],
  exports: [
    AboutComponent,
    HelpComponent,
    HomeComponent,
    NotFoundComponent,
    ProfileComponent,

  ],
  bootstrap: [],
})
export class MyspaceModule { }

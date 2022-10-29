import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { DealsComponent } from './deals/deals.component';
import { AllproductsComponent } from './allproducts/allproducts.component';
import { TopsellComponent } from './topsell/topsell.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    DealsComponent,
    AllproductsComponent,
    TopsellComponent,
   
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

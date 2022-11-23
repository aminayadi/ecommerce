import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { MapComponent } from './components/map/map.component';
import { CategoriesComponent } from './components/categories/categories.component';
import { ProductsComponent } from './components/products/products.component';

import { HttpClientModule } from '@angular/common/http';
import { SwiperModule } from 'swiper/angular';
import { AccountComponent } from './components/account/account.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { AuthComponent } from './components/auth/auth.component';




@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MapComponent,
    CategoriesComponent,
    ProductsComponent,
    AccountComponent,
    LoginComponent,
    RegisterComponent,
    AuthComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,  
    HttpClientModule,
    SwiperModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

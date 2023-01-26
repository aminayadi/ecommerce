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
import { HomeComponent } from './home/home.component';
import { FooderComponent } from './components/fooder/fooder.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { CategoryModule } from './category/category.module';
import { ProductModule } from './product/product.module';
import { FieldsModule } from './fields/fields.module';
import { ClientComponent } from './components/client/client.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MyspaceModule } from './myspace/myspace.module';
import { MyspaceComponent } from './myspace/myspace.component';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MapComponent,
    CategoriesComponent,
    ProductsComponent,
    AccountComponent,
    HomeComponent,
    FooderComponent,
    ClientComponent,
    MyspaceComponent
   
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,  
    HttpClientModule,
    SwiperModule,
    FormsModule,
    ReactiveFormsModule,
    CategoryModule,
    ProductModule,
    FieldsModule,
    BrowserAnimationsModule,
    MyspaceModule,

    BrowserAnimationsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
  ],
  

  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }

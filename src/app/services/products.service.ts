import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from 'src/app/model/product';


@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http: HttpClient) { }
  getAll() {
    //return this.http.get<Product[]>("http://localhost:8080/services/categorydb/api/products");
    return this.http.get<Product[]>("http://localhost:8085/api/products");
  }
}

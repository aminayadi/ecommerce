import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from 'src/app/model/product';


@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http: HttpClient) { }
  getAll() {
    return this.http.get<Product[]>("http://localhost:8085/api/productsforcurrent");
  }

  create(payload: Product) {
    return this.http.post<Product>('http://localhost:8085/api/products', payload);
  }

  delete(id:String){
    return this.http.delete<Product>(`http://localhost:8085/api/products/${id}`);
 }

 getById(id: String) {
  return this.http.get<Product>(`http://localhost:8085/api/products/${id}`);
 }

 update(payload:Product){
   console.log("Product : UPDATING ..... ", payload);
  return this.http.put(`http://localhost:8085/api/products/${payload.id}`,payload);
 }
//******** JN  ADDING  GET ALL PRODUCTS*********//
 getProducts(){
  return this.http.get<Product[]>("http://localhost:8085/api/products");
 }

}

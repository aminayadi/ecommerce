import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from 'src/app/model/product';


@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http: HttpClient) { }
  getAll() {
    return this.http.get<Product[]>("http://localhost:8080/services/productdb/api/productsforcurrent");
  }

  create(payload: Product) {
    return this.http.post<Product>('http://localhost:8080/services/productdb/api/products', payload);
  }

  delete(id:String){
    return this.http.delete<Product>(`http://localhost:8080/services/productdb/api/products/${id}`);
 }

 getById(id: String) {
  return this.http.get<Product>(`http://localhost:8080/services/productdb/api/products/${id}`);
 }

 update(payload:Product){
  return this.http.put(`http://localhost:8080/services/productdb/api/products/${payload.id}`,payload);
 }

}

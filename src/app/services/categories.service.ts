import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Categorie } from 'src/app/model/categorie';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {

  constructor(private http: HttpClient) { }
  getAll() {
    console.log("get all categories ........");
    //return this.http.get<Categorie[]>("http://localhost:8080/services/categorydb/api/categories");
    return this.http.get<Categorie[]>("http://localhost:8081/api/categories");
  }

  create(payload: Categorie) {
    return this.http.post<Categorie>('http://localhost:8081/api/categories', payload);
  }



}

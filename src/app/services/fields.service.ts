import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Fields } from '../model/fields';

@Injectable({
  providedIn: 'root'
})
export class FieldsService {

  

  constructor(private http: HttpClient) { }
  getallbyIdCategory(id:String) {
    console.log("get all categories ........");
    //return this.http.get<Categorie[]>("http://localhost:8080/services/categorydb/api/categories");
    return this.http.get<Fields[]>(`http://localhost:8081/api/fields/category/${id}`);
  }

  create(payload: Fields) {
    return this.http.post<Fields>('http://localhost:8081/api/fields', payload);
  }


  getById(id: String) {
    return this.http.get<Fields>(`http://localhost:8081/api/fields/${id}`);
   }
    
   update(payload:Fields){
    return this.http.put(`http://localhost:8081/api/fields/${payload.id}`,payload);
   }

   delete(id:String){
    return this.http.delete<Fields>(`http://localhost:8081/api/fields/${id}`);
 }

}

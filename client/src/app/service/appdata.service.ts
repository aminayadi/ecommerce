import {Injectable}   from '@angular/core';
import {LocalStorage} from 'ngx-webstorage';

@Injectable() 
export class AppDataService {
  
  @LocalStorage()
  public id: string;

  @LocalStorage()
  public fname: string;

  public clearData(){
    this.id = null;
    this.fname = null;
  }

}
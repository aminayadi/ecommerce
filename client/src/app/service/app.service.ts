import {Injectable}   from '@angular/core';
import {XHRHandler}   from './xhrhandler.service';
import {LoginRequest} from '../data/loginrequest'
import {Observable}   from 'rxjs';

@Injectable() 
export class AppService {
  constructor(private xhrhandler: XHRHandler) {}

  userLogin(request: LoginRequest): Observable<any> {
    return this.xhrhandler.doPost('api/hetuser', request);
  }

  listUser(): Observable<any> {
    return this.xhrhandler.doGet('api/hetusers');
  }
}
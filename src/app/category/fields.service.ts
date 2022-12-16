import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FieldsService {


  onDeleteField: Subject<number>;

  constructor() {
    this.onDeleteField = new Subject<number>();
  }

  deleteField(index: number) {
    this.onDeleteField.next(index);
  }
}
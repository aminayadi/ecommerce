import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FieldsService } from '../fields.service'

@Component({
  selector: 'app-field',
  templateUrl: './field.component.html',
  styleUrls: ['./field.component.css']
})
export class FieldComponent implements OnInit {

  @Input() fieldForm!: FormGroup;
  @Input() index!: number;

  constructor(
    private _fieldFormService: FieldsService) {
  }

  ngOnInit() {
  }

  deleteField() {
    console.log(this.index);
    this._fieldFormService.deleteField(this.index);
  }

  canShowError(controlName: string) {
    const control = this.fieldForm.get(controlName);
    return control!.invalid && (control!.dirty || control!.touched)
  }

  hasError(controlName: string, errorProp: string) {
    const control = this.fieldForm.get(controlName);
    return control!.errors![errorProp]
  }
}



import { Component, OnInit, Input } from '@angular/core';
import { FormArray, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-field-list',
  templateUrl: './field-list.component.html',
  styleUrls: ['./field-list.component.css']
})
export class FieldListComponent implements OnInit {

  @Input() fields!: FormArray;

  get fieldsControls() {
    return this.fields.controls as FormGroup[];
  }

  constructor() { }

  ngOnInit() {
  }

}



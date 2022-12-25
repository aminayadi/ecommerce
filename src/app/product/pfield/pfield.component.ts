import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { Pfield } from 'src/app/model/pfield';

@Component({
  selector: 'app-pfield',
  templateUrl: './pfield.component.html',
  styleUrls: ['./pfield.component.css']
})
export class PfieldComponent implements OnInit {
  productForm!: FormGroup;
  @Input() pfield!: Pfield;


  constructor(private _formBuilder: FormBuilder) { 

    this.productForm = this._formBuilder.group({
      name: this.pfield.name,
      value: ''

  });



  }

  ngOnInit(): void {
  }

}

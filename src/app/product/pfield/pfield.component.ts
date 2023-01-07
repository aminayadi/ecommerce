import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Fields } from 'src/app/model/fields';

import { Pfield } from 'src/app/model/pfield';

@Component({
  selector: 'app-pfield',
  templateUrl: './pfield.component.html',
  styleUrls: ['./pfield.component.css']
})
export class PfieldComponent implements OnInit {
  
  _pfield: Pfield | null = null;
  @Input() pfield: Fields| null = null;

  pfieldForm!: FormGroup;

  constructor(private _formBuilder: FormBuilder) { 
    this.pfieldForm = this._formBuilder.group({
      value: ''

  });

 

  }

  ngOnInit(): void {
    console.log("Enter to PFieldComponent ------------: ",this.pfield);
    if (this.pfield != null) {
      this._pfield = new Pfield();
      this._pfield.name =  this.pfield.name ;
      this._pfield.type = this.pfield.type ;
      //    this._pfield = this.pfield;
          console.log("Enter to PFieldComponent : ",this._pfield);
        }

  }

}

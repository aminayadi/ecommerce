import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormArray, FormGroup, FormControl, Validators } from '@angular/forms';
import { Categorie } from 'src/app/model/categorie';
import { CategoriesService } from 'src/app/services/categories.service';
import { FieldsService } from '../fields.service';
@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  categories  : Categorie[] = [];
  categoryForm: FormGroup;
  fields!: FormArray

  constructor(
    private categoriesService:CategoriesService,
    private _fieldsService: FieldsService,
    private _formBuilder: FormBuilder) {
    this.fields = this._formBuilder.array([
      this._formBuilder.group({
        name: ['', Validators.required],
        type: ['', Validators.required]
      })
    ])

    this.categoryForm = this._formBuilder.group({
        name: ['', Validators.required],
        parent: ['', Validators.required],
        fields: this.fields,
    });

    this._fieldsService.onDeleteField
      .subscribe(this.deleteField.bind(this));
  }

  ngOnInit() {
    this.getCategories();
  }

  addField() {
  
    this.fields.push(
      this._formBuilder.group({
        name: ['', Validators.required],
        type: ['', Validators.required]
      })
    )
  }

  deleteField(index: number) {
    this.fields.removeAt(index);
  }

  canShowError(controlName: string) {
    const control = this.categoryForm.get(controlName);
    return control!.invalid && (control!.dirty || control!.touched)
  }

  hasError(controlName: string, errorProp: string) {
    const control = this.categoryForm.get(controlName);
    return control!.errors![errorProp]
  }

  submit() {
    console.log(this.categoryForm.value);
    this.categoriesService.create(this.categoryForm.value)
    .subscribe({
      next:(data) => {
        console.log("success .....");
      },
      error:(err) => {
        console.log(err);
      }
    })
  }

  getCategories() {
    console.log("Enter to get categories ...... ");
    this.categoriesService.getAll().subscribe((data) => {
      this. categories = data;
      console.log(data);
    });
  }


}
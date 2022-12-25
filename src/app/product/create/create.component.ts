import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Categorie } from 'src/app/model/categorie';
import { Fields } from 'src/app/model/fields';
import { Pfield } from 'src/app/model/pfield';
import { CategoriesService } from 'src/app/services/categories.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {
  categories  : Categorie[] = [];
  fields: Fields[] = [];
  productForm!: FormGroup;

  constructor(private categoriesService:CategoriesService,private _formBuilder: FormBuilder) { 

    this.productForm = this._formBuilder.group({
      name: ['', Validators.required],
      category: ['', Validators.required],

  });



  }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories() {
    console.log("Enter to get categories ...... ");
    this.categoriesService.getAll().subscribe((data) => {
      this.categories = data;
      console.log(data);
    });
  }

  fillFields(categ:Event){
    console.log("Enter to fillFields ...... ",this.productForm.get('category'));
    let _category=this.productForm.get('category');
    let mother = _category!.value!;

    while (mother!=null)
    {
      let _fields: Fields[] = mother!.fields;
      this.fields.concat(_fields);
      mother = mother!.mother!;
    }
    console.log("fields : ", this.fields);

  }

  submit() {
    console.log(this.productForm.value);
  /*  this.categoriesService.create(this.categoryForm.value)
    .subscribe({
      next:(data) => {
        console.log("success .....");
      },
      error:(err) => {
        console.log(err);
      }
    })*/
  }

  hasError(controlName: string, errorProp: string) {
    const control = this.productForm.get(controlName);
    return control!.errors![errorProp]
  }

  canShowError(controlName: string) {
    const control = this.productForm.get(controlName);
    return control!.invalid && (control!.dirty || control!.touched)
  }  
}

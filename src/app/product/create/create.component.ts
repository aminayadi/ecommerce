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
  categories  :any =[];
  fields: Fields[] = [];
  productForm!: FormGroup;
  _category!: any;

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
      console.log("this.categories : ......", this.categories);
    });
  }

  fillFields(categ:Event){
    console.log("Enter to fillFields ...... ",this.productForm.get('category'));
    this._category=this.productForm.get('category');
    console.log("_category : ", this._category);
    let mother = this._category!.value!;
    console.log("mother : ", mother);

    while (mother!=null)
    {
      let _fields=this.getFieldsofCategories(mother.id);//     : Fields[] = mother!.fields;
      console.log("mother.id = ", mother.id, "_fields : ", _fields);
      if (_fields != null)
        {
            this.fields = this.fields.concat(_fields);
            console.log("fields : ", this.fields);
        }

      mother = mother!.mother!;
      
      console.log("mother : ", mother);
    }
    console.log("END ------------- fields : ", this.fields);

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

  getFieldsofCategories(id: string):Fields[]{
    console.log("this.categories: ", this.categories,"  this.categories.length ", this.categories.length);
    for(let i=0; i< this.categories.length; i++)
     
    {
      console.log("this._category[i].id : ", this.categories[i].id, " id = ", id);
      if (this.categories[i].id == id)
      {
        return this.categories[i].fields;
      }
    }
    return [];
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

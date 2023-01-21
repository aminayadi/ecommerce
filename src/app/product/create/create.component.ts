import { Component, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Categorie } from 'src/app/model/categorie';
import { Fields } from 'src/app/model/fields';
import { Pfield } from 'src/app/model/pfield';
import { CategoriesService } from 'src/app/services/categories.service';
import { ProductsService } from 'src/app/services/products.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { UploadImagesComponent } from '../upload-images/upload-images.component';


@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {
  @ViewChild('child')
  child!: UploadImagesComponent;
  
  categories  :any =[];
  fields: Fields[] = [];
  productForm!: FormGroup;
  pfieldForm!: FormGroup;
  _category!: any;
  mother:Categorie | undefined;



  private roles: string[] = [];
  isLoggedIn = false;
  username?: string;

  constructor(private categoriesService:CategoriesService,
    private _formBuilder: FormBuilder,
    private productsService: ProductsService,
    private tokenStorageService: TokenStorageService) { 

    this.productForm = this._formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      zone: ['', Validators.required],
      category: ['', Validators.required],
      pfields:this._formBuilder.array([])

  });




  }

  get pfields() {
    return this.productForm.controls["pfields"] as FormArray;
  }


  addNewPfields(name:string){
    this.pfieldForm = this._formBuilder.group({
      name: new FormControl(''),
      value: new FormControl(''),
    });
    this.pfieldForm.get("name")?.setValue(name);
    this.pfieldForm.get("value")?.setValue("");
    this.pfields.push(this.pfieldForm);
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

  getCategoryById(idCateg:any)
  {
    let res:Categorie;
    console.log("getCategoryById ++++++++++ this.categories : ......", this.categories);
    for (let i=0; i< this.categories.length; i++){

      console.log("categ .........hello : ", this.categories[i]);
      if (this.categories[i].id == idCateg)
      {
        return this.categories[i];
      }      
    }
    return null ; 
  }

  fillFields(categ:Event){
    this.fields=[];
    this._category=this.productForm.get('category');
    console.log("_category : ", this._category);
    let mother = this._category!.value!;


    while (mother!=null)
    {
      let _fields=this.getFieldsofCategories(mother.id);//     : Fields[] = mother!.fields;
      if (_fields != null)
        {
            this.fields = this.fields.concat(_fields);
            console.log("fields : ", this.fields);
            
        }

        //get mother category from this.categories function to add  
      if(mother!.mother)
        mother = this.getCategoryById(mother!.mother!.id);// mother!.mother!;
      else
        mother = null; 
      
    }
    console.log("END ------------- fieldçs : ", this.fields);
    this.pfields.clear();
    console.log("PFIELDS ------------- CLEARED : ", this.pfields);

    for(let i=0; i<this.fields.length;i++)
    {
      this.addNewPfields(this.fields[i].name);
  
      console.log("PFIELDS ------------- PFIELDS : ", this.pfields);
    }
    

  }

  submit() {
    console.log(this.productForm.value);
    console.log(this.child.imageInfos);
   // console.log(this.productForm.value.pfields[0]);
    
    this.productsService.create(this.productForm.value)
    .subscribe({
      next:(data) => {
        console.log("success .....");
      },
      error:(err) => {
        console.log(err);
      }
    })
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

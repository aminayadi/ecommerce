import { Component, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Categorie } from 'src/app/model/categorie';
import { Fields } from 'src/app/model/fields';
import { Pfield } from 'src/app/model/pfield';
import { Photo } from 'src/app/model/photo';
import { CategoriesService } from 'src/app/services/categories.service';
import { DataUtils, FileLoadError } from 'src/app/services/data-util.service';
import { EventManager, EventWithContent } from 'src/app/services/event-manager.service';
import { ProductsService } from 'src/app/services/products.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { UploadImagesComponent } from '../upload-images/upload-images.component';




@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {
  
  imageInfos?: Observable<any>;
  @ViewChild('child', {static: false})  child!: UploadImagesComponent;

  categories  :any =[];
  fields: Fields[] = [];
  productForm!: FormGroup;
  pfieldForm!: FormGroup;
  _category!: any;
  mother:Categorie | undefined;

  private roles: string[] = [];
  isLoggedIn = false;
  username?: string;

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    private categoriesService:CategoriesService,
    private _formBuilder: FormBuilder,
    private productsService: ProductsService,
    private tokenStorageService: TokenStorageService) { 

    this.productForm = this._formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      zone: ['', Validators.required],
      price: ['', Validators.required],
      discount: [''],
      afterdiscount:[''],
      photo: null,
      photoContentType: null,
      category: ['', Validators.required],
      pfields:this._formBuilder.array([]),
      lphotos:[],
      idcategory:['']
 
  });




  }

  get pfields() {
    return this.productForm.controls["pfields"] as FormArray;
  }

  ngAfterViewInit() {
    this.imageInfos = this.child.imageInfos;
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
    for (let i=0; i< this.categories.length; i++){

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
   // this.pfieldForm.value.idcategory = this._category.id;
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

    this.pfields.clear();


    for(let i=0; i<this.fields.length;i++)
    {
      this.addNewPfields(this.fields[i].name);
  
      console.log("PFIELDS ------------- PFIELDS : ", this.pfields);
    }
    

  }

   submit() {
    this.productForm.value.idcategory = this.productForm.value.category.name ; 
    console.log("Submitting .... :) ", this.productForm.value);
   
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


  public doSomethingWithCount(obj:any):void {
   
    const imageInfos= obj.imageInfos ;
    const nb=obj.nb;
   
    console.log("nb = ***********************************", nb);

    this.imageInfos = imageInfos;

    console.log("imageInfos-----------",this.imageInfos) ;
    //. . . More logic
    let lphotos:Photo[]=[];

    this.imageInfos!.forEach((element) => 
    {  
      console.log("FROM CREATE FORM : -----------",element);

      
      for (let j=element.length-nb; j<element.length;j++)
      {
        let p:Photo={
          path: element[j].url,
          name: element[j].name,
          type: '',
          id: null
        }
        lphotos.push(p);
        
    }

    console.log("LPHOTOS :) ", lphotos);
    
    this.productForm.controls['lphotos'].setValue(lphotos);
   
    console.log("doSomethingWithCount :) ", this.productForm.value);
    console.log("LPHOTOS :) ", lphotos);
    
  }

    );


    

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


//--------------JN ADD MAIN PHOTO STARTS--------//



byteSize(base64String: string): string {
  return this.dataUtils.byteSize(base64String);
}

openFile(base64String: string, contentType: string | null | undefined): void {
  this.dataUtils.openFile(base64String, contentType);
}

setFileData(event: Event, field: string, isImage: boolean): void {
  this.dataUtils.loadFileToForm(event, this.productForm, field, isImage).subscribe({
    error: (err: FileLoadError) =>
      this.eventManager.broadcast('big error'),
  });
}



//--------------JN ADD MAIN PHOTO ENDS--------//











}




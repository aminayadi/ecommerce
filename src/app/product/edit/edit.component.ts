import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Pfield } from 'src/app/model/pfield';
import { Photo } from 'src/app/model/photo';
import { Product } from 'src/app/model/product';
import { CategoriesService } from 'src/app/services/categories.service';
import { ProductsService } from 'src/app/services/products.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  
  @Input() index!: String;
  pfieldForm!: FormGroup;
  productForm!: FormGroup;
  imageInfos?: Observable<any>;
  id!:String ;

  constructor(private route: ActivatedRoute,
    private router:Router,
    private categoriesService:CategoriesService,
    private fb: FormBuilder,
    private productsService: ProductsService,
    private tokenStorageService: TokenStorageService) { 

      this.productForm = this.fb.group({
        id: ['', Validators.required],
        name: ['', Validators.required],
        idcategory: ['', Validators.required],
        iduser: ['', Validators.required],
        description: ['', Validators.required],
        zone: ['', Validators.required],
        price: [0, Validators.required],
        discount: [0],
    
        lphotos:[],
        photoContentType:null,
        photo:null,
        createdat:null,
        updatedat:null,

        pfields:this.fb.array([]),
    });

  }

  addPfield(_pfield:Pfield) {
    const pField = this.fb.group({
        name: [_pfield.name, Validators.required],
        value: [_pfield.value, Validators.required]
    });
  
    this.pfields.push(pField);
    console.log("this.zone : ",this.productForm.get('zone'));
    console.log("this.pfields : ",this.pfields);
    console.log("this.pfields ******: ",this.productForm.get('pfields'));
  }


  ngOnInit(): void {

    this.route.paramMap.subscribe((param) => {
      this.id = String(param.get('id'));
      this.getById(this.id);
      console.log("this.id %%%%%%%%%= ", this.id);
    });
/*
    if (this.index)
    {
      console.log("this.index = ", this.index);
      this.getById(this.index);


    }
*/
   // console.log("this.pfields = ", this.productForm.controls["pfields"]);
  }

  
  getById(id: String) {
    this.productsService.getById(id).subscribe((data) => {

      console.log("data : .......................................", data);
      console.log("this.product form : .......................................", this.productForm);

      for (let i=0; i<data.pfields.length;i++)
        this.addPfield(data.pfields[i]);


      this.productForm.setValue(data);

      

    });
  }
  
  get pfields() {
    return this.productForm.controls["pfields"] as FormArray;
  }
  
  update() {
    this.productsService.update(this.productForm.value)
    .subscribe({
      next:(data) => {
        console.log("success .....");
       // this.router.navigate(["/myspace/announce/null"]);
      },
      error:(err) => {
        console.log(err);
      }
    })
  }

  public doSomethingWithCount(imageInfos: Observable<any>):void {
  
    
    this.imageInfos = imageInfos;
    //. . . More logic
    let lphotos:Photo[] = [];
    this.imageInfos!.forEach((element) => 
    {
      console.log("FROM CREATE FORM : -----------",element);
      for (let j=0; j<element.length;j++)
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
  }

    );




}


}

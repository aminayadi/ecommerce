import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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
  
  productForm: Product = {
    id :'',
    name :'',
    idcategory:'',
    iduser: '',
    description: '',
    zone:'',
    lphotos:[],
    photo: null,
    photo_content_type:null   
  };

  constructor(private route: ActivatedRoute,
    private router:Router,
    private categoriesService:CategoriesService,
    private _formBuilder: FormBuilder,
    private productsService: ProductsService,
    private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {

    this.route.paramMap.subscribe((param) => {
      var id = String(param.get('id'));
      this.getById(id);
    });

    if (this.index)
    {
      console.log("this.index = ", this.index);
      this.getById(this.index);
    }
  }

  getById(id: String) {
    this.productsService.getById(id).subscribe((data) => {
      this.productForm = data;
    });
  }
  
  update() {
    this.productsService.update(this.productForm)
    .subscribe({
      next:(data) => {
        this.router.navigate(["/product/list"]);
      },
      error:(err) => {
        console.log(err);
      }
    })
  }

}

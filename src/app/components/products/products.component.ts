import { Component, OnInit, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { ProductsService } from 'src/app/services/products.service';
import { Product } from 'src/app/model/product';
@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  allproducts ! : Product[];
  products  : Product[] = [];
  constructor(private elementRef: ElementRef, public _router: Router, private productsService:ProductsService) { }
  ngOnInit() {

    
  
    this.get();
  }
  get() {
    this.productsService.getAll().subscribe((data) => {
      this. allproducts = data;
/************************** 
      for (let i=0;i<this.allproducts.length;i++)
        if(this.allproducts[i].name==null)
          this.products.push(this.allproducts[i]);
*********************************/
      console.log(data);
    });
  }
}
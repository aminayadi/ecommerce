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

    var aa = document.createElement("script");
    aa.type = "text/javascript";
    aa.src ="../../assets/js/vendor/jquery.js";
    this.elementRef.nativeElement.appendChild(aa);

   
   





    var b = document.createElement("script");
    b.type = "text/javascript";
    b.src ="../../assets/js/bootstrap-bundle.js";
    this.elementRef.nativeElement.appendChild(b);



    var e = document.createElement("script");
    e.type = "text/javascript";
    e.src ="../../assets/js/imagesloaded-pkgd.js";
    this.elementRef.nativeElement.appendChild(e);

  



  
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
import { Component, OnInit, ElementRef } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  
  constructor(private elementRef: ElementRef, public _router: Router) { }
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

    var f = document.createElement("script");
    f.type = "text/javascript";
    f.src ="../../assets/js/isotope-pkgd.js";
    this.elementRef.nativeElement.appendChild(f);


   

    var i = document.createElement("script");
    i.type = "text/javascript";
    i.src ="../../assets/js/nice-select.js";
    this.elementRef.nativeElement.appendChild(i);

    
  



    var m = document.createElement("script");
    m.type = "text/javascript";
    m.src ="../../assets/js/tweenmax.js";
    this.elementRef.nativeElement.appendChild(m);


    var n = document.createElement("script");
    n.type = "text/javascript";
    n.src ="../../assets/js/ui-slider-range.js";
    this.elementRef.nativeElement.appendChild(n);

    var o = document.createElement("script");
    o.type = "text/javascript";
    o.src ="../../assets/js/wow.js";
    this.elementRef.nativeElement.appendChild(o);

 
  
   
  }
}
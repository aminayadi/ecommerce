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

    var aaa = document.createElement("script");
    aaa.type = "text/javascript";
    aaa.src ="../../assets/js/vendor/waypoints.js";
    this.elementRef.nativeElement.appendChild(aaa);

   


    var a = document.createElement("script");
    a.type = "text/javascript";
    a.src ="../../assets/js/backtotop.js";
    this.elementRef.nativeElement.appendChild(a);



    var b = document.createElement("script");
    b.type = "text/javascript";
    b.src ="../../assets/js/bootstrap-bundle.js";
    this.elementRef.nativeElement.appendChild(b);

    var c = document.createElement("script");
    c.type = "text/javascript";
    c.src ="../../assets/js/countdown.min.js";
    this.elementRef.nativeElement.appendChild(c);

    var d = document.createElement("script");
    d.type = "text/javascript";
    d.src ="../../assets/js/counterup.js";
    this.elementRef.nativeElement.appendChild(d);

    var e = document.createElement("script");
    e.type = "text/javascript";
    e.src ="../../assets/js/imagesloaded-pkgd.js";
    this.elementRef.nativeElement.appendChild(e);

    var f = document.createElement("script");
    f.type = "text/javascript";
    f.src ="../../assets/js/isotope-pkgd.js";
    this.elementRef.nativeElement.appendChild(f);


    var g = document.createElement("script");
    g.type = "text/javascript";
    g.src ="../../assets/js/magnific-popup.js";
    this.elementRef.nativeElement.appendChild(g);
   
    var h = document.createElement("script");
    h.type = "text/javascript";
    h.src ="../../assets/js/meanmenu.js";
    this.elementRef.nativeElement.appendChild(h);

    var i = document.createElement("script");
    i.type = "text/javascript";
    i.src ="../../assets/js/nice-select.js";
    this.elementRef.nativeElement.appendChild(i);

    var j = document.createElement("script");
    j.type = "text/javascript";
    j.src ="../../assets/js/owl-carousel.js";
    this.elementRef.nativeElement.appendChild(j);
    
    var k = document.createElement("script");
    k.type = "text/javascript";
    k.src ="../../assets/js/parallax.js";
    this.elementRef.nativeElement.appendChild(k);


    var l = document.createElement("script");
    l.type = "text/javascript";
    l.src ="../../assets/js/swiper-bundle.js";
    this.elementRef.nativeElement.appendChild(l);

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

 
    var p = document.createElement("script");
    p.type = "text/javascript";
    p.src ="../../assets/js/ajax-form.js";
    this.elementRef.nativeElement.appendChild(p);
    
    var q = document.createElement("script");
    q.type = "text/javascript";
    q.src ="../../assets/js/main.js";
    this.elementRef.nativeElement.appendChild(q);
   
  }
}
import { Component, OnInit, ElementRef } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {


  constructor(private elementRef: ElementRef, public _router: Router) { }
  ngOnInit() {

    var s = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../../assets/js/map.js";
    this.elementRef.nativeElement.appendChild(s);
  }
}
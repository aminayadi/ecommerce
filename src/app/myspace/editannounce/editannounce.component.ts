import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-editannounce',
  templateUrl: './editannounce.component.html',
  styleUrls: ['./editannounce.component.css']
})
export class EditannounceComponent implements OnInit {
  @Input() index!: String; 
  constructor() { }

  ngOnInit(): void {
  }

}

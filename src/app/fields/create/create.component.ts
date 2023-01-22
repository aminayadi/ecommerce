import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Categorie } from 'src/app/model/categorie';
import { Fields } from 'src/app/model/fields';
import { FieldsService } from 'src/app/services/fields.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  fieldForm: Fields = {
    id: '',
    name: '',
    type:'',
    category:new Categorie
  };

  constructor(private fieldService:FieldsService,
              private router:Router) { }



  ngOnInit(): void {
  }

  create(){
    console.log("------enter create-----");
    console.log(this.fieldForm);
    this.fieldService.create(this.fieldForm)
    
    .subscribe({
     
      next:(data) => {
         
        this.router.navigate(["/fields/list"])
        
      },
      error:(err) => {
        console.log(err);
      }

     
    })
     
    console.log("------quit create-----");
  }


}

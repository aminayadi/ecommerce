import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Categorie } from 'src/app/model/categorie';
import { Fields } from 'src/app/model/fields';
import { FieldsService } from 'src/app/services/fields.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  fieldForm: Fields = {
    id: '',
    name: '',
    type:'',
    category:new Categorie,
  };
  
  constructor(private route: ActivatedRoute,
              private router:Router,
              private fieldsService: FieldsService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((param) => {
      var id = String(param.get('id'));
      this.getById(id);
    });
    
  }

  getById(id: String) {
    this.fieldsService.getById(id).subscribe((data) => {
      this.fieldForm = data;
    });
  }


  update() {
    this.fieldsService.update(this.fieldForm)
    .subscribe({
      next:(data) => {
        this.router.navigate(["/fields/list"]);
      },
      error:(err) => {
        console.log(err);
      }
    })
  }

}

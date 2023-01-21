import { Component, OnInit } from '@angular/core';
import {  ActivatedRoute, Router } from '@angular/router';
import { Categorie } from 'src/app/model/categorie';
import { CategoriesService } from 'src/app/services/categories.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  

  categoryForm: Categorie = {
    id :'',
    name :'',
    idparent:0,
    parent:'',
    mother:null,
  };

  constructor(private categoriesService: CategoriesService,
              private route: ActivatedRoute,
              private router:Router) { }

  ngOnInit(): void {

    this.route.paramMap.subscribe((param) => {
      var id = String(param.get('id'));
      this.getById(id);
    });
  }

  getById(id: String) {
    this.categoriesService.getById(id).subscribe((data) => {
      this.categoryForm = data;
    });
  }
  
  update() {
    this.categoriesService.update(this.categoryForm)
    .subscribe({
      next:(data) => {
        this.router.navigate(["/category/list"]);
      },
      error:(err) => {
        console.log(err);
      }
    })
  }

}

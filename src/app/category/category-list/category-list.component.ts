import { Component, Input, OnInit } from '@angular/core';

import { Categorie } from 'src/app/model/categorie';

import { CategoriesService } from 'src/app/services/categories.service';

declare var window: any;


@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  allcategories: Categorie[] = [];
  categories  : Categorie[] = [];
  
  deleteModal: any;
  idTodelete: String = '';
 
  constructor(private categoriesService: CategoriesService) { }

  ngOnInit(): void {

    this.deleteModal = new window.bootstrap.Modal(
      document.getElementById('deleteModal')
    );

    this.get();
  }

  get() {
    this.categoriesService.getAll().subscribe((data) => {
      this. allcategories = data;

      for (let i=0;i<this.allcategories.length;i++)
        if(this.allcategories[i].parent==null)
          this.categories.push(this.allcategories[i]);

      console.log(data);
    });
  }


  openDeleteModal(id: String) {
    this.idTodelete = id;
    this.deleteModal.show();
  }

  delete() {
    this.categoriesService.delete(this.idTodelete).subscribe({
      next: (data) => {
        this.allcategories = this.allcategories.filter(_ => _.id != this.idTodelete)
        this.deleteModal.hide();
      },
    });
  }


  
}

import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/model/product';
import { ProductsService } from 'src/app/services/products.service';

declare var window: any;

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  allproducts: Product[] = [];

  deleteModal: any;
  idTodelete: String = '';
 
  constructor(private productsService: ProductsService) { }

  ngOnInit(): void {

    this.deleteModal = new window.bootstrap.Modal(
      document.getElementById('deleteModal')
    );

    this.get();
  }

  get() {
    this.productsService.getAll().subscribe((data) => {
      this. allproducts = data;
      console.log(data);
    });
  }


  openDeleteModal(id: String) {
    this.idTodelete = id;
    this.deleteModal.show();
  }

  delete() {
    this.productsService.delete(this.idTodelete).subscribe({
      next: (data) => {
        this.allproducts = this.allproducts.filter(_ => _.id != this.idTodelete)
        this.deleteModal.hide();
      },
    });
  }


  
}
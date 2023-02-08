import { Component, OnInit } from '@angular/core';
import { Product, ProductDto } from 'src/app/model/product';
import { ProductsService } from 'src/app/services/products.service';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent implements OnInit {
  products: Product[] = [];
  productsDto:  ProductDto[] = [];
  
  

  constructor(private productsService: ProductsService) { 
    
  }

  ngOnInit(): void {



    this.productsService.getProducts().subscribe((data: Product[]) => {
      this.products = data;
      this.productsDto = this.inintProductDto(this.products);
    
      console.log(this.products);
  
    });


  }
  




inintProductDto(products: Product[]):ProductDto[] {
  let tempProductDto: ProductDto[] = [];
 
  products.forEach((product) => {

    const prodDto: ProductDto = {

    id:product.id,
    idcategory:product.idcategory,
    iduser:product.iduser,
    name:product.name,
    photo:this.getPhoto(product.photo),
   
    description:product.description,
    zone:product.zone,
    lphotos:[],
    pfield:[],
    
    };

    tempProductDto.push(prodDto);

   });
  
  return tempProductDto;
  
}
 
private getPhoto(data: string): any {
 
  return 'data:image/jpg;base64,' + data;
}

url:string="assets/img/products/sm1p1.jpg";

imageChange(event: any){

  this.url = event.target.src;
}





} 



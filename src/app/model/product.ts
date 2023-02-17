import { Pfield } from "./pfield";
import { Photo } from "./photo";

export class Product {
    [x: string]: any;
    id!:string;
    idcategory!:string;
    iduser!:string;
    name!:string;
    //photo!:Blob | null;
    //photoContentType!:string |null;
    photo:any;

    description!:string;
    zone!:string;
    price!:number;
    lphotos!:Photo[];
    pfields!:Pfield[];
    
    
    constructor() {
        this.id= '';
        this.idcategory= '';
        this.iduser= '';
        this.name= '';
        this.photo= '';
        this.description= '';
        this.zone= '';
        this.price= 0;
        this.lphotos=[];
        this.pfields=[];  
      }
    
     


}



export interface ProductDto {
   
    id:string;
    idcategory:string;
    iduser:string;
    name:string;
    photo:any;
    description:string;
    zone:string;
    price:number;
    lphotos:Photo[];
    pfield:Pfield[];
    
  }



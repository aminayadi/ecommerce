import { Photo } from "./photo";

export class Product {
    id!:string;
    idcategory!:string;
    iduser!:string;
    name!:string;
    photo!:Blob;
    photo_content_type!:string;
    description!:string;
    zone!:string;
    lphotos!:Photo[];
    

}




import { Photo } from "./photo";

export class Product {
    id!:string;
    idcategory!:string;
    iduser!:string;
    name!:string;
    photo!:Blob | null;
    photo_content_type!:string |null;
    description!:string;
    zone!:string;
    lphotos!:Photo[];
    

}




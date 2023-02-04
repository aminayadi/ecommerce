import { Pfield } from "./pfield";
import { Photo } from "./photo";

export class Product {
    [x: string]: any;
    id!:string;
    idcategory!:string;
    iduser!:string;
    name!:string;
    photo!:Blob | null;
    photoContentType!:string |null;
    description!:string;
    zone!:string;
    lphotos!:Photo[];
    pfields!:Pfield[];
    

}




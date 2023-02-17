import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProduct, NewProduct } from '../product.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProduct for edit and NewProductFormGroupInput for create.
 */
type ProductFormGroupInput = IProduct | PartialWithRequiredKeyOf<NewProduct>;

type ProductFormDefaults = Pick<NewProduct, 'id'>;

type ProductFormGroupContent = {
  id: FormControl<IProduct['id'] | NewProduct['id']>;
  idcategory: FormControl<IProduct['idcategory']>;
  iduser: FormControl<IProduct['iduser']>;
  name: FormControl<IProduct['name']>;
  photo: FormControl<IProduct['photo']>;
  photoContentType: FormControl<IProduct['photoContentType']>;
  description: FormControl<IProduct['description']>;
  zone: FormControl<IProduct['zone']>;
  price: FormControl<IProduct['price']>;
  discount: FormControl<IProduct['discount']>;
  afterdiscount: FormControl<IProduct['afterdiscount']>;
  createdat: FormControl<IProduct['createdat']>;
  updatedat: FormControl<IProduct['updatedat']>;
};

export type ProductFormGroup = FormGroup<ProductFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductFormService {
  createProductFormGroup(product: ProductFormGroupInput = { id: null }): ProductFormGroup {
    const productRawValue = {
      ...this.getFormDefaults(),
      ...product,
    };
    return new FormGroup<ProductFormGroupContent>({
      id: new FormControl(
        { value: productRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idcategory: new FormControl(productRawValue.idcategory),
      iduser: new FormControl(productRawValue.iduser),
      name: new FormControl(productRawValue.name),
      photo: new FormControl(productRawValue.photo),
      photoContentType: new FormControl(productRawValue.photoContentType),
      description: new FormControl(productRawValue.description),
      zone: new FormControl(productRawValue.zone),
      price: new FormControl(productRawValue.price),
      discount: new FormControl(productRawValue.discount),
      afterdiscount: new FormControl(productRawValue.afterdiscount),
      createdat: new FormControl(productRawValue.createdat),
      updatedat: new FormControl(productRawValue.updatedat),
    });
  }

  getProduct(form: ProductFormGroup): IProduct | NewProduct {
    return form.getRawValue() as IProduct | NewProduct;
  }

  resetForm(form: ProductFormGroup, product: ProductFormGroupInput): void {
    const productRawValue = { ...this.getFormDefaults(), ...product };
    form.reset(
      {
        ...productRawValue,
        id: { value: productRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductFormDefaults {
    return {
      id: null,
    };
  }
}

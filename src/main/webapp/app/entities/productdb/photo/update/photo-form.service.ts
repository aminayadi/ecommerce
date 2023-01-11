import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPhoto, NewPhoto } from '../photo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPhoto for edit and NewPhotoFormGroupInput for create.
 */
type PhotoFormGroupInput = IPhoto | PartialWithRequiredKeyOf<NewPhoto>;

type PhotoFormDefaults = Pick<NewPhoto, 'id'>;

type PhotoFormGroupContent = {
  id: FormControl<IPhoto['id'] | NewPhoto['id']>;
  path: FormControl<IPhoto['path']>;
  type: FormControl<IPhoto['type']>;
  product: FormControl<IPhoto['product']>;
};

export type PhotoFormGroup = FormGroup<PhotoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PhotoFormService {
  createPhotoFormGroup(photo: PhotoFormGroupInput = { id: null }): PhotoFormGroup {
    const photoRawValue = {
      ...this.getFormDefaults(),
      ...photo,
    };
    return new FormGroup<PhotoFormGroupContent>({
      id: new FormControl(
        { value: photoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      path: new FormControl(photoRawValue.path, {
        validators: [Validators.required],
      }),
      type: new FormControl(photoRawValue.type),
      product: new FormControl(photoRawValue.product, {
        validators: [Validators.required],
      }),
    });
  }

  getPhoto(form: PhotoFormGroup): IPhoto | NewPhoto {
    return form.getRawValue() as IPhoto | NewPhoto;
  }

  resetForm(form: PhotoFormGroup, photo: PhotoFormGroupInput): void {
    const photoRawValue = { ...this.getFormDefaults(), ...photo };
    form.reset(
      {
        ...photoRawValue,
        id: { value: photoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PhotoFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFavoris, NewFavoris } from '../favoris.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFavoris for edit and NewFavorisFormGroupInput for create.
 */
type FavorisFormGroupInput = IFavoris | PartialWithRequiredKeyOf<NewFavoris>;

type FavorisFormDefaults = Pick<NewFavoris, 'id'>;

type FavorisFormGroupContent = {
  id: FormControl<IFavoris['id'] | NewFavoris['id']>;
  idproduct: FormControl<IFavoris['idproduct']>;
  iduser: FormControl<IFavoris['iduser']>;
  createdat: FormControl<IFavoris['createdat']>;
  modifiedat: FormControl<IFavoris['modifiedat']>;
  deletedat: FormControl<IFavoris['deletedat']>;
};

export type FavorisFormGroup = FormGroup<FavorisFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FavorisFormService {
  createFavorisFormGroup(favoris: FavorisFormGroupInput = { id: null }): FavorisFormGroup {
    const favorisRawValue = {
      ...this.getFormDefaults(),
      ...favoris,
    };
    return new FormGroup<FavorisFormGroupContent>({
      id: new FormControl(
        { value: favorisRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idproduct: new FormControl(favorisRawValue.idproduct),
      iduser: new FormControl(favorisRawValue.iduser),
      createdat: new FormControl(favorisRawValue.createdat),
      modifiedat: new FormControl(favorisRawValue.modifiedat),
      deletedat: new FormControl(favorisRawValue.deletedat),
    });
  }

  getFavoris(form: FavorisFormGroup): IFavoris | NewFavoris {
    return form.getRawValue() as IFavoris | NewFavoris;
  }

  resetForm(form: FavorisFormGroup, favoris: FavorisFormGroupInput): void {
    const favorisRawValue = { ...this.getFormDefaults(), ...favoris };
    form.reset(
      {
        ...favorisRawValue,
        id: { value: favorisRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FavorisFormDefaults {
    return {
      id: null,
    };
  }
}

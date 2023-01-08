import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPfield, NewPfield } from '../pfield.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPfield for edit and NewPfieldFormGroupInput for create.
 */
type PfieldFormGroupInput = IPfield | PartialWithRequiredKeyOf<NewPfield>;

type PfieldFormDefaults = Pick<NewPfield, 'id'>;

type PfieldFormGroupContent = {
  id: FormControl<IPfield['id'] | NewPfield['id']>;
  name: FormControl<IPfield['name']>;
  type: FormControl<IPfield['type']>;
  value: FormControl<IPfield['value']>;
  product: FormControl<IPfield['product']>;
};

export type PfieldFormGroup = FormGroup<PfieldFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PfieldFormService {
  createPfieldFormGroup(pfield: PfieldFormGroupInput = { id: null }): PfieldFormGroup {
    const pfieldRawValue = {
      ...this.getFormDefaults(),
      ...pfield,
    };
    return new FormGroup<PfieldFormGroupContent>({
      id: new FormControl(
        { value: pfieldRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(pfieldRawValue.name, {
        validators: [Validators.required],
      }),
      type: new FormControl(pfieldRawValue.type, {
        validators: [Validators.required],
      }),
      value: new FormControl(pfieldRawValue.value),
      product: new FormControl(pfieldRawValue.product, {
        validators: [Validators.required],
      }),
    });
  }

  getPfield(form: PfieldFormGroup): IPfield | NewPfield {
    return form.getRawValue() as IPfield | NewPfield;
  }

  resetForm(form: PfieldFormGroup, pfield: PfieldFormGroupInput): void {
    const pfieldRawValue = { ...this.getFormDefaults(), ...pfield };
    form.reset(
      {
        ...pfieldRawValue,
        id: { value: pfieldRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PfieldFormDefaults {
    return {
      id: null,
    };
  }
}

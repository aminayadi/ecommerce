import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFields, NewFields } from '../fields.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFields for edit and NewFieldsFormGroupInput for create.
 */
type FieldsFormGroupInput = IFields | PartialWithRequiredKeyOf<NewFields>;

type FieldsFormDefaults = Pick<NewFields, 'id'>;

type FieldsFormGroupContent = {
  id: FormControl<IFields['id'] | NewFields['id']>;
  name: FormControl<IFields['name']>;
  type: FormControl<IFields['type']>;
  category: FormControl<IFields['category']>;
};

export type FieldsFormGroup = FormGroup<FieldsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FieldsFormService {
  createFieldsFormGroup(fields: FieldsFormGroupInput = { id: null }): FieldsFormGroup {
    const fieldsRawValue = {
      ...this.getFormDefaults(),
      ...fields,
    };
    return new FormGroup<FieldsFormGroupContent>({
      id: new FormControl(
        { value: fieldsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(fieldsRawValue.name, {
        validators: [Validators.required],
      }),
      type: new FormControl(fieldsRawValue.type, {
        validators: [Validators.required],
      }),
      category: new FormControl(fieldsRawValue.category, {
        validators: [Validators.required],
      }),
    });
  }

  getFields(form: FieldsFormGroup): IFields | NewFields {
    return form.getRawValue() as IFields | NewFields;
  }

  resetForm(form: FieldsFormGroup, fields: FieldsFormGroupInput): void {
    const fieldsRawValue = { ...this.getFormDefaults(), ...fields };
    form.reset(
      {
        ...fieldsRawValue,
        id: { value: fieldsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FieldsFormDefaults {
    return {
      id: null,
    };
  }
}

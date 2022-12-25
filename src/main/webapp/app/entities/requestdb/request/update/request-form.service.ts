import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRequest, NewRequest } from '../request.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRequest for edit and NewRequestFormGroupInput for create.
 */
type RequestFormGroupInput = IRequest | PartialWithRequiredKeyOf<NewRequest>;

type RequestFormDefaults = Pick<NewRequest, 'id'>;

type RequestFormGroupContent = {
  id: FormControl<IRequest['id'] | NewRequest['id']>;
  iduser: FormControl<IRequest['iduser']>;
  idcategory: FormControl<IRequest['idcategory']>;
  idproduct: FormControl<IRequest['idproduct']>;
  subject: FormControl<IRequest['subject']>;
  description: FormControl<IRequest['description']>;
  createdat: FormControl<IRequest['createdat']>;
  modifiedat: FormControl<IRequest['modifiedat']>;
  deletedat: FormControl<IRequest['deletedat']>;
};

export type RequestFormGroup = FormGroup<RequestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RequestFormService {
  createRequestFormGroup(request: RequestFormGroupInput = { id: null }): RequestFormGroup {
    const requestRawValue = {
      ...this.getFormDefaults(),
      ...request,
    };
    return new FormGroup<RequestFormGroupContent>({
      id: new FormControl(
        { value: requestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      iduser: new FormControl(requestRawValue.iduser),
      idcategory: new FormControl(requestRawValue.idcategory),
      idproduct: new FormControl(requestRawValue.idproduct),
      subject: new FormControl(requestRawValue.subject),
      description: new FormControl(requestRawValue.description),
      createdat: new FormControl(requestRawValue.createdat),
      modifiedat: new FormControl(requestRawValue.modifiedat),
      deletedat: new FormControl(requestRawValue.deletedat),
    });
  }

  getRequest(form: RequestFormGroup): IRequest | NewRequest {
    return form.getRawValue() as IRequest | NewRequest;
  }

  resetForm(form: RequestFormGroup, request: RequestFormGroupInput): void {
    const requestRawValue = { ...this.getFormDefaults(), ...request };
    form.reset(
      {
        ...requestRawValue,
        id: { value: requestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RequestFormDefaults {
    return {
      id: null,
    };
  }
}

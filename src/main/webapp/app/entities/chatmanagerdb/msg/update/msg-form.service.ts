import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMsg, NewMsg } from '../msg.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMsg for edit and NewMsgFormGroupInput for create.
 */
type MsgFormGroupInput = IMsg | PartialWithRequiredKeyOf<NewMsg>;

type MsgFormDefaults = Pick<NewMsg, 'id'>;

type MsgFormGroupContent = {
  id: FormControl<IMsg['id'] | NewMsg['id']>;
  type: FormControl<IMsg['type']>;
  from: FormControl<IMsg['from']>;
  fromUserName: FormControl<IMsg['fromUserName']>;
  message: FormControl<IMsg['message']>;
};

export type MsgFormGroup = FormGroup<MsgFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MsgFormService {
  createMsgFormGroup(msg: MsgFormGroupInput = { id: null }): MsgFormGroup {
    const msgRawValue = {
      ...this.getFormDefaults(),
      ...msg,
    };
    return new FormGroup<MsgFormGroupContent>({
      id: new FormControl(
        { value: msgRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(msgRawValue.type),
      from: new FormControl(msgRawValue.from, {
        validators: [Validators.required],
      }),
      fromUserName: new FormControl(msgRawValue.fromUserName, {
        validators: [Validators.required],
      }),
      message: new FormControl(msgRawValue.message, {
        validators: [Validators.required],
      }),
    });
  }

  getMsg(form: MsgFormGroup): IMsg | NewMsg {
    return form.getRawValue() as IMsg | NewMsg;
  }

  resetForm(form: MsgFormGroup, msg: MsgFormGroupInput): void {
    const msgRawValue = { ...this.getFormDefaults(), ...msg };
    form.reset(
      {
        ...msgRawValue,
        id: { value: msgRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MsgFormDefaults {
    return {
      id: null,
    };
  }
}

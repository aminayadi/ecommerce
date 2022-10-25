import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMessage, NewMessage } from '../message.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMessage for edit and NewMessageFormGroupInput for create.
 */
type MessageFormGroupInput = IMessage | PartialWithRequiredKeyOf<NewMessage>;

type MessageFormDefaults = Pick<NewMessage, 'id'>;

type MessageFormGroupContent = {
  id: FormControl<IMessage['id'] | NewMessage['id']>;
  iduser: FormControl<IMessage['iduser']>;
  idproduct: FormControl<IMessage['idproduct']>;
  idsender: FormControl<IMessage['idsender']>;
  idreceiver: FormControl<IMessage['idreceiver']>;
  subject: FormControl<IMessage['subject']>;
  description: FormControl<IMessage['description']>;
  createdat: FormControl<IMessage['createdat']>;
  hiddenat: FormControl<IMessage['hiddenat']>;
};

export type MessageFormGroup = FormGroup<MessageFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MessageFormService {
  createMessageFormGroup(message: MessageFormGroupInput = { id: null }): MessageFormGroup {
    const messageRawValue = {
      ...this.getFormDefaults(),
      ...message,
    };
    return new FormGroup<MessageFormGroupContent>({
      id: new FormControl(
        { value: messageRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      iduser: new FormControl(messageRawValue.iduser),
      idproduct: new FormControl(messageRawValue.idproduct),
      idsender: new FormControl(messageRawValue.idsender),
      idreceiver: new FormControl(messageRawValue.idreceiver),
      subject: new FormControl(messageRawValue.subject),
      description: new FormControl(messageRawValue.description),
      createdat: new FormControl(messageRawValue.createdat),
      hiddenat: new FormControl(messageRawValue.hiddenat),
    });
  }

  getMessage(form: MessageFormGroup): IMessage | NewMessage {
    return form.getRawValue() as IMessage | NewMessage;
  }

  resetForm(form: MessageFormGroup, message: MessageFormGroupInput): void {
    const messageRawValue = { ...this.getFormDefaults(), ...message };
    form.reset(
      {
        ...messageRawValue,
        id: { value: messageRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MessageFormDefaults {
    return {
      id: null,
    };
  }
}

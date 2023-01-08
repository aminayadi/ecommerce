import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IResearch, NewResearch } from '../research.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IResearch for edit and NewResearchFormGroupInput for create.
 */
type ResearchFormGroupInput = IResearch | PartialWithRequiredKeyOf<NewResearch>;

type ResearchFormDefaults = Pick<NewResearch, 'id'>;

type ResearchFormGroupContent = {
  id: FormControl<IResearch['id'] | NewResearch['id']>;
  iduser: FormControl<IResearch['iduser']>;
  idcategory: FormControl<IResearch['idcategory']>;
  idzone: FormControl<IResearch['idzone']>;
  createdat: FormControl<IResearch['createdat']>;
  updatedat: FormControl<IResearch['updatedat']>;
  zone: FormControl<IResearch['zone']>;
};

export type ResearchFormGroup = FormGroup<ResearchFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ResearchFormService {
  createResearchFormGroup(research: ResearchFormGroupInput = { id: null }): ResearchFormGroup {
    const researchRawValue = {
      ...this.getFormDefaults(),
      ...research,
    };
    return new FormGroup<ResearchFormGroupContent>({
      id: new FormControl(
        { value: researchRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      iduser: new FormControl(researchRawValue.iduser),
      idcategory: new FormControl(researchRawValue.idcategory),
      idzone: new FormControl(researchRawValue.idzone),
      createdat: new FormControl(researchRawValue.createdat),
      updatedat: new FormControl(researchRawValue.updatedat),
      zone: new FormControl(researchRawValue.zone),
    });
  }

  getResearch(form: ResearchFormGroup): IResearch | NewResearch {
    return form.getRawValue() as IResearch | NewResearch;
  }

  resetForm(form: ResearchFormGroup, research: ResearchFormGroupInput): void {
    const researchRawValue = { ...this.getFormDefaults(), ...research };
    form.reset(
      {
        ...researchRawValue,
        id: { value: researchRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ResearchFormDefaults {
    return {
      id: null,
    };
  }
}

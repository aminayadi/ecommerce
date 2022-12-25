import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FieldsFormService, FieldsFormGroup } from './fields-form.service';
import { IFields } from '../fields.model';
import { FieldsService } from '../service/fields.service';
import { ICategory } from 'app/entities/categorydb/category/category.model';
import { CategoryService } from 'app/entities/categorydb/category/service/category.service';
import { etype } from 'app/entities/enumerations/etype.model';

@Component({
  selector: 'jhi-fields-update',
  templateUrl: './fields-update.component.html',
})
export class FieldsUpdateComponent implements OnInit {
  isSaving = false;
  fields: IFields | null = null;
  etypeValues = Object.keys(etype);

  categoriesSharedCollection: ICategory[] = [];

  editForm: FieldsFormGroup = this.fieldsFormService.createFieldsFormGroup();

  constructor(
    protected fieldsService: FieldsService,
    protected fieldsFormService: FieldsFormService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCategory = (o1: ICategory | null, o2: ICategory | null): boolean => this.categoryService.compareCategory(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fields }) => {
      this.fields = fields;
      if (fields) {
        this.updateForm(fields);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fields = this.fieldsFormService.getFields(this.editForm);
    if (fields.id !== null) {
      this.subscribeToSaveResponse(this.fieldsService.update(fields));
    } else {
      this.subscribeToSaveResponse(this.fieldsService.create(fields));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFields>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(fields: IFields): void {
    this.fields = fields;
    this.fieldsFormService.resetForm(this.editForm, fields);

    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing<ICategory>(
      this.categoriesSharedCollection,
      fields.category
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing<ICategory>(categories, this.fields?.category)
        )
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));
  }
}

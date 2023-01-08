import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PfieldFormService, PfieldFormGroup } from './pfield-form.service';
import { IPfield } from '../pfield.model';
import { PfieldService } from '../service/pfield.service';
import { IProduct } from 'app/entities/productdb/product/product.model';
import { ProductService } from 'app/entities/productdb/product/service/product.service';
import { etype } from 'app/entities/enumerations/etype.model';

@Component({
  selector: 'jhi-pfield-update',
  templateUrl: './pfield-update.component.html',
})
export class PfieldUpdateComponent implements OnInit {
  isSaving = false;
  pfield: IPfield | null = null;
  etypeValues = Object.keys(etype);

  productsSharedCollection: IProduct[] = [];

  editForm: PfieldFormGroup = this.pfieldFormService.createPfieldFormGroup();

  constructor(
    protected pfieldService: PfieldService,
    protected pfieldFormService: PfieldFormService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProduct = (o1: IProduct | null, o2: IProduct | null): boolean => this.productService.compareProduct(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pfield }) => {
      this.pfield = pfield;
      if (pfield) {
        this.updateForm(pfield);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pfield = this.pfieldFormService.getPfield(this.editForm);
    if (pfield.id !== null) {
      this.subscribeToSaveResponse(this.pfieldService.update(pfield));
    } else {
      this.subscribeToSaveResponse(this.pfieldService.create(pfield));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPfield>>): void {
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

  protected updateForm(pfield: IPfield): void {
    this.pfield = pfield;
    this.pfieldFormService.resetForm(this.editForm, pfield);

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing<IProduct>(
      this.productsSharedCollection,
      pfield.product
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(map((products: IProduct[]) => this.productService.addProductToCollectionIfMissing<IProduct>(products, this.pfield?.product)))
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));
  }
}

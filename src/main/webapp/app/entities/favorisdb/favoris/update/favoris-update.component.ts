import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FavorisFormService, FavorisFormGroup } from './favoris-form.service';
import { IFavoris } from '../favoris.model';
import { FavorisService } from '../service/favoris.service';

@Component({
  selector: 'jhi-favoris-update',
  templateUrl: './favoris-update.component.html',
})
export class FavorisUpdateComponent implements OnInit {
  isSaving = false;
  favoris: IFavoris | null = null;

  editForm: FavorisFormGroup = this.favorisFormService.createFavorisFormGroup();

  constructor(
    protected favorisService: FavorisService,
    protected favorisFormService: FavorisFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ favoris }) => {
      this.favoris = favoris;
      if (favoris) {
        this.updateForm(favoris);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const favoris = this.favorisFormService.getFavoris(this.editForm);
    if (favoris.id !== null) {
      this.subscribeToSaveResponse(this.favorisService.update(favoris));
    } else {
      this.subscribeToSaveResponse(this.favorisService.create(favoris));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFavoris>>): void {
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

  protected updateForm(favoris: IFavoris): void {
    this.favoris = favoris;
    this.favorisFormService.resetForm(this.editForm, favoris);
  }
}

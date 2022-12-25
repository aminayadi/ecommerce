import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ResearchFormService, ResearchFormGroup } from './research-form.service';
import { IResearch } from '../research.model';
import { ResearchService } from '../service/research.service';

@Component({
  selector: 'jhi-research-update',
  templateUrl: './research-update.component.html',
})
export class ResearchUpdateComponent implements OnInit {
  isSaving = false;
  research: IResearch | null = null;

  editForm: ResearchFormGroup = this.researchFormService.createResearchFormGroup();

  constructor(
    protected researchService: ResearchService,
    protected researchFormService: ResearchFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ research }) => {
      this.research = research;
      if (research) {
        this.updateForm(research);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const research = this.researchFormService.getResearch(this.editForm);
    if (research.id !== null) {
      this.subscribeToSaveResponse(this.researchService.update(research));
    } else {
      this.subscribeToSaveResponse(this.researchService.create(research));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResearch>>): void {
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

  protected updateForm(research: IResearch): void {
    this.research = research;
    this.researchFormService.resetForm(this.editForm, research);
  }
}

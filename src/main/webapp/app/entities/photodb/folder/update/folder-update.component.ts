import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FolderFormService, FolderFormGroup } from './folder-form.service';
import { IFolder } from '../folder.model';
import { FolderService } from '../service/folder.service';

@Component({
  selector: 'jhi-folder-update',
  templateUrl: './folder-update.component.html',
})
export class FolderUpdateComponent implements OnInit {
  isSaving = false;
  folder: IFolder | null = null;

  editForm: FolderFormGroup = this.folderFormService.createFolderFormGroup();

  constructor(
    protected folderService: FolderService,
    protected folderFormService: FolderFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ folder }) => {
      this.folder = folder;
      if (folder) {
        this.updateForm(folder);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const folder = this.folderFormService.getFolder(this.editForm);
    if (folder.id !== null) {
      this.subscribeToSaveResponse(this.folderService.update(folder));
    } else {
      this.subscribeToSaveResponse(this.folderService.create(folder));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFolder>>): void {
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

  protected updateForm(folder: IFolder): void {
    this.folder = folder;
    this.folderFormService.resetForm(this.editForm, folder);
  }
}

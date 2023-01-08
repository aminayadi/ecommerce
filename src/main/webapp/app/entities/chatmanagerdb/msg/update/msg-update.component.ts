import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MsgFormService, MsgFormGroup } from './msg-form.service';
import { IMsg } from '../msg.model';
import { MsgService } from '../service/msg.service';

@Component({
  selector: 'jhi-msg-update',
  templateUrl: './msg-update.component.html',
})
export class MsgUpdateComponent implements OnInit {
  isSaving = false;
  msg: IMsg | null = null;

  editForm: MsgFormGroup = this.msgFormService.createMsgFormGroup();

  constructor(protected msgService: MsgService, protected msgFormService: MsgFormService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ msg }) => {
      this.msg = msg;
      if (msg) {
        this.updateForm(msg);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const msg = this.msgFormService.getMsg(this.editForm);
    if (msg.id !== null) {
      this.subscribeToSaveResponse(this.msgService.update(msg));
    } else {
      this.subscribeToSaveResponse(this.msgService.create(msg));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMsg>>): void {
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

  protected updateForm(msg: IMsg): void {
    this.msg = msg;
    this.msgFormService.resetForm(this.editForm, msg);
  }
}

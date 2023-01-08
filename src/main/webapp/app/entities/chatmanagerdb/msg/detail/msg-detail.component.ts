import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMsg } from '../msg.model';

@Component({
  selector: 'jhi-msg-detail',
  templateUrl: './msg-detail.component.html',
})
export class MsgDetailComponent implements OnInit {
  msg: IMsg | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ msg }) => {
      this.msg = msg;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

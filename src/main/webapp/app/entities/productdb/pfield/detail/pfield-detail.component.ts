import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPfield } from '../pfield.model';

@Component({
  selector: 'jhi-pfield-detail',
  templateUrl: './pfield-detail.component.html',
})
export class PfieldDetailComponent implements OnInit {
  pfield: IPfield | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pfield }) => {
      this.pfield = pfield;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

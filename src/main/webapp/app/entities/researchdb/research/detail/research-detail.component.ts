import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResearch } from '../research.model';

@Component({
  selector: 'jhi-research-detail',
  templateUrl: './research-detail.component.html',
})
export class ResearchDetailComponent implements OnInit {
  research: IResearch | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ research }) => {
      this.research = research;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFavoris } from '../favoris.model';

@Component({
  selector: 'jhi-favoris-detail',
  templateUrl: './favoris-detail.component.html',
})
export class FavorisDetailComponent implements OnInit {
  favoris: IFavoris | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ favoris }) => {
      this.favoris = favoris;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

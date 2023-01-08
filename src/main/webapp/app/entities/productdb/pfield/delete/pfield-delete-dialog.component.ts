import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPfield } from '../pfield.model';
import { PfieldService } from '../service/pfield.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './pfield-delete-dialog.component.html',
})
export class PfieldDeleteDialogComponent {
  pfield?: IPfield;

  constructor(protected pfieldService: PfieldService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.pfieldService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

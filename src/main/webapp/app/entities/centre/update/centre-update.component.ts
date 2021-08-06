import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICentre, Centre } from '../centre.model';
import { CentreService } from '../service/centre.service';
import { IArrondissement } from 'app/entities/arrondissement/arrondissement.model';
import { ArrondissementService } from 'app/entities/arrondissement/service/arrondissement.service';

@Component({
  selector: 'jhi-centre-update',
  templateUrl: './centre-update.component.html',
})
export class CentreUpdateComponent implements OnInit {
  isSaving = false;

  arrondissementsSharedCollection: IArrondissement[] = [];

  editForm = this.fb.group({
    id: [],
    centreName: [],
    centreChief: [],
    arrondissement: [],
  });

  constructor(
    protected centreService: CentreService,
    protected arrondissementService: ArrondissementService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centre }) => {
      this.updateForm(centre);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centre = this.createFromForm();
    if (centre.id !== undefined) {
      this.subscribeToSaveResponse(this.centreService.update(centre));
    } else {
      this.subscribeToSaveResponse(this.centreService.create(centre));
    }
  }

  trackArrondissementById(index: number, item: IArrondissement): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentre>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
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

  protected updateForm(centre: ICentre): void {
    this.editForm.patchValue({
      id: centre.id,
      centreName: centre.centreName,
      centreChief: centre.centreChief,
      arrondissement: centre.arrondissement,
    });

    this.arrondissementsSharedCollection = this.arrondissementService.addArrondissementToCollectionIfMissing(
      this.arrondissementsSharedCollection,
      centre.arrondissement
    );
  }

  protected loadRelationshipsOptions(): void {
    this.arrondissementService
      .query()
      .pipe(map((res: HttpResponse<IArrondissement[]>) => res.body ?? []))
      .pipe(
        map((arrondissements: IArrondissement[]) =>
          this.arrondissementService.addArrondissementToCollectionIfMissing(arrondissements, this.editForm.get('arrondissement')!.value)
        )
      )
      .subscribe((arrondissements: IArrondissement[]) => (this.arrondissementsSharedCollection = arrondissements));
  }

  protected createFromForm(): ICentre {
    return {
      ...new Centre(),
      id: this.editForm.get(['id'])!.value,
      centreName: this.editForm.get(['centreName'])!.value,
      centreChief: this.editForm.get(['centreChief'])!.value,
      arrondissement: this.editForm.get(['arrondissement'])!.value,
    };
  }
}

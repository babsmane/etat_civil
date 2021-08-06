import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IArrondissement, Arrondissement } from '../arrondissement.model';
import { ArrondissementService } from '../service/arrondissement.service';
import { ICommune } from 'app/entities/commune/commune.model';
import { CommuneService } from 'app/entities/commune/service/commune.service';

@Component({
  selector: 'jhi-arrondissement-update',
  templateUrl: './arrondissement-update.component.html',
})
export class ArrondissementUpdateComponent implements OnInit {
  isSaving = false;

  communesSharedCollection: ICommune[] = [];

  editForm = this.fb.group({
    id: [],
    arrondissementCode: [],
    arrondissementName: [null, [Validators.required]],
    commune: [],
  });

  constructor(
    protected arrondissementService: ArrondissementService,
    protected communeService: CommuneService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arrondissement }) => {
      this.updateForm(arrondissement);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const arrondissement = this.createFromForm();
    if (arrondissement.id !== undefined) {
      this.subscribeToSaveResponse(this.arrondissementService.update(arrondissement));
    } else {
      this.subscribeToSaveResponse(this.arrondissementService.create(arrondissement));
    }
  }

  trackCommuneById(index: number, item: ICommune): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArrondissement>>): void {
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

  protected updateForm(arrondissement: IArrondissement): void {
    this.editForm.patchValue({
      id: arrondissement.id,
      arrondissementCode: arrondissement.arrondissementCode,
      arrondissementName: arrondissement.arrondissementName,
      commune: arrondissement.commune,
    });

    this.communesSharedCollection = this.communeService.addCommuneToCollectionIfMissing(
      this.communesSharedCollection,
      arrondissement.commune
    );
  }

  protected loadRelationshipsOptions(): void {
    this.communeService
      .query()
      .pipe(map((res: HttpResponse<ICommune[]>) => res.body ?? []))
      .pipe(
        map((communes: ICommune[]) => this.communeService.addCommuneToCollectionIfMissing(communes, this.editForm.get('commune')!.value))
      )
      .subscribe((communes: ICommune[]) => (this.communesSharedCollection = communes));
  }

  protected createFromForm(): IArrondissement {
    return {
      ...new Arrondissement(),
      id: this.editForm.get(['id'])!.value,
      arrondissementCode: this.editForm.get(['arrondissementCode'])!.value,
      arrondissementName: this.editForm.get(['arrondissementName'])!.value,
      commune: this.editForm.get(['commune'])!.value,
    };
  }
}

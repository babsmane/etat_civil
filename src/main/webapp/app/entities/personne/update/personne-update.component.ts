import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPersonne, Personne } from '../personne.model';
import { PersonneService } from '../service/personne.service';
import { ICentre } from 'app/entities/centre/centre.model';
import { CentreService } from 'app/entities/centre/service/centre.service';

@Component({
  selector: 'jhi-personne-update',
  templateUrl: './personne-update.component.html',
})
export class PersonneUpdateComponent implements OnInit {
  isSaving = false;

  centresSharedCollection: ICentre[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    fatherName: [],
    sexe: [],
    motherFirstName: [],
    motherLastName: [],
    dateOfBirthday: [],
    hourOfBithday: [],
    numberRegister: [],
    centre: [],
  });

  constructor(
    protected personneService: PersonneService,
    protected centreService: CentreService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personne }) => {
      if (personne.id === undefined) {
        const today = dayjs().startOf('day');
        personne.dateOfBirthday = today;
        personne.hourOfBithday = today;
      }

      this.updateForm(personne);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personne = this.createFromForm();
    if (personne.id !== undefined) {
      this.subscribeToSaveResponse(this.personneService.update(personne));
    } else {
      this.subscribeToSaveResponse(this.personneService.create(personne));
    }
  }

  trackCentreById(index: number, item: ICentre): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonne>>): void {
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

  protected updateForm(personne: IPersonne): void {
    this.editForm.patchValue({
      id: personne.id,
      firstName: personne.firstName,
      lastName: personne.lastName,
      fatherName: personne.fatherName,
      sexe: personne.sexe,
      motherFirstName: personne.motherFirstName,
      motherLastName: personne.motherLastName,
      dateOfBirthday: personne.dateOfBirthday ? personne.dateOfBirthday.format(DATE_TIME_FORMAT) : null,
      hourOfBithday: personne.hourOfBithday ? personne.hourOfBithday.format(DATE_TIME_FORMAT) : null,
      numberRegister: personne.numberRegister,
      centre: personne.centre,
    });

    this.centresSharedCollection = this.centreService.addCentreToCollectionIfMissing(this.centresSharedCollection, personne.centre);
  }

  protected loadRelationshipsOptions(): void {
    this.centreService
      .query()
      .pipe(map((res: HttpResponse<ICentre[]>) => res.body ?? []))
      .pipe(map((centres: ICentre[]) => this.centreService.addCentreToCollectionIfMissing(centres, this.editForm.get('centre')!.value)))
      .subscribe((centres: ICentre[]) => (this.centresSharedCollection = centres));
  }

  protected createFromForm(): IPersonne {
    return {
      ...new Personne(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      fatherName: this.editForm.get(['fatherName'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      motherFirstName: this.editForm.get(['motherFirstName'])!.value,
      motherLastName: this.editForm.get(['motherLastName'])!.value,
      dateOfBirthday: this.editForm.get(['dateOfBirthday'])!.value
        ? dayjs(this.editForm.get(['dateOfBirthday'])!.value, DATE_TIME_FORMAT)
        : undefined,
      hourOfBithday: this.editForm.get(['hourOfBithday'])!.value
        ? dayjs(this.editForm.get(['hourOfBithday'])!.value, DATE_TIME_FORMAT)
        : undefined,
      numberRegister: this.editForm.get(['numberRegister'])!.value,
      centre: this.editForm.get(['centre'])!.value,
    };
  }
}

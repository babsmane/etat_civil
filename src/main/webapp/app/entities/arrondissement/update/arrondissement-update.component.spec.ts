jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ArrondissementService } from '../service/arrondissement.service';
import { IArrondissement, Arrondissement } from '../arrondissement.model';
import { ICommune } from 'app/entities/commune/commune.model';
import { CommuneService } from 'app/entities/commune/service/commune.service';

import { ArrondissementUpdateComponent } from './arrondissement-update.component';

describe('Component Tests', () => {
  describe('Arrondissement Management Update Component', () => {
    let comp: ArrondissementUpdateComponent;
    let fixture: ComponentFixture<ArrondissementUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let arrondissementService: ArrondissementService;
    let communeService: CommuneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ArrondissementUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ArrondissementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArrondissementUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      arrondissementService = TestBed.inject(ArrondissementService);
      communeService = TestBed.inject(CommuneService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Commune query and add missing value', () => {
        const arrondissement: IArrondissement = { id: 456 };
        const commune: ICommune = { id: 92990 };
        arrondissement.commune = commune;

        const communeCollection: ICommune[] = [{ id: 78056 }];
        spyOn(communeService, 'query').and.returnValue(of(new HttpResponse({ body: communeCollection })));
        const additionalCommunes = [commune];
        const expectedCollection: ICommune[] = [...additionalCommunes, ...communeCollection];
        spyOn(communeService, 'addCommuneToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ arrondissement });
        comp.ngOnInit();

        expect(communeService.query).toHaveBeenCalled();
        expect(communeService.addCommuneToCollectionIfMissing).toHaveBeenCalledWith(communeCollection, ...additionalCommunes);
        expect(comp.communesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const arrondissement: IArrondissement = { id: 456 };
        const commune: ICommune = { id: 51578 };
        arrondissement.commune = commune;

        activatedRoute.data = of({ arrondissement });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(arrondissement));
        expect(comp.communesSharedCollection).toContain(commune);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const arrondissement = { id: 123 };
        spyOn(arrondissementService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ arrondissement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: arrondissement }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(arrondissementService.update).toHaveBeenCalledWith(arrondissement);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const arrondissement = new Arrondissement();
        spyOn(arrondissementService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ arrondissement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: arrondissement }));
        saveSubject.complete();

        // THEN
        expect(arrondissementService.create).toHaveBeenCalledWith(arrondissement);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const arrondissement = { id: 123 };
        spyOn(arrondissementService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ arrondissement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(arrondissementService.update).toHaveBeenCalledWith(arrondissement);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCommuneById', () => {
        it('Should return tracked Commune primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCommuneById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});

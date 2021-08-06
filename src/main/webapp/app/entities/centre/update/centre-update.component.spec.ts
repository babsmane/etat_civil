jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CentreService } from '../service/centre.service';
import { ICentre, Centre } from '../centre.model';
import { IArrondissement } from 'app/entities/arrondissement/arrondissement.model';
import { ArrondissementService } from 'app/entities/arrondissement/service/arrondissement.service';

import { CentreUpdateComponent } from './centre-update.component';

describe('Component Tests', () => {
  describe('Centre Management Update Component', () => {
    let comp: CentreUpdateComponent;
    let fixture: ComponentFixture<CentreUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let centreService: CentreService;
    let arrondissementService: ArrondissementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CentreUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CentreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CentreUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      centreService = TestBed.inject(CentreService);
      arrondissementService = TestBed.inject(ArrondissementService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Arrondissement query and add missing value', () => {
        const centre: ICentre = { id: 456 };
        const arrondissement: IArrondissement = { id: 47498 };
        centre.arrondissement = arrondissement;

        const arrondissementCollection: IArrondissement[] = [{ id: 79851 }];
        spyOn(arrondissementService, 'query').and.returnValue(of(new HttpResponse({ body: arrondissementCollection })));
        const additionalArrondissements = [arrondissement];
        const expectedCollection: IArrondissement[] = [...additionalArrondissements, ...arrondissementCollection];
        spyOn(arrondissementService, 'addArrondissementToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ centre });
        comp.ngOnInit();

        expect(arrondissementService.query).toHaveBeenCalled();
        expect(arrondissementService.addArrondissementToCollectionIfMissing).toHaveBeenCalledWith(
          arrondissementCollection,
          ...additionalArrondissements
        );
        expect(comp.arrondissementsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const centre: ICentre = { id: 456 };
        const arrondissement: IArrondissement = { id: 68056 };
        centre.arrondissement = arrondissement;

        activatedRoute.data = of({ centre });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(centre));
        expect(comp.arrondissementsSharedCollection).toContain(arrondissement);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const centre = { id: 123 };
        spyOn(centreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ centre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: centre }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(centreService.update).toHaveBeenCalledWith(centre);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const centre = new Centre();
        spyOn(centreService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ centre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: centre }));
        saveSubject.complete();

        // THEN
        expect(centreService.create).toHaveBeenCalledWith(centre);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const centre = { id: 123 };
        spyOn(centreService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ centre });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(centreService.update).toHaveBeenCalledWith(centre);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackArrondissementById', () => {
        it('Should return tracked Arrondissement primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackArrondissementById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});

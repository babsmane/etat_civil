jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PersonneService } from '../service/personne.service';
import { IPersonne, Personne } from '../personne.model';
import { ICentre } from 'app/entities/centre/centre.model';
import { CentreService } from 'app/entities/centre/service/centre.service';

import { PersonneUpdateComponent } from './personne-update.component';

describe('Component Tests', () => {
  describe('Personne Management Update Component', () => {
    let comp: PersonneUpdateComponent;
    let fixture: ComponentFixture<PersonneUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let personneService: PersonneService;
    let centreService: CentreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PersonneUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PersonneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonneUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      personneService = TestBed.inject(PersonneService);
      centreService = TestBed.inject(CentreService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Centre query and add missing value', () => {
        const personne: IPersonne = { id: 456 };
        const centre: ICentre = { id: 73308 };
        personne.centre = centre;

        const centreCollection: ICentre[] = [{ id: 84230 }];
        spyOn(centreService, 'query').and.returnValue(of(new HttpResponse({ body: centreCollection })));
        const additionalCentres = [centre];
        const expectedCollection: ICentre[] = [...additionalCentres, ...centreCollection];
        spyOn(centreService, 'addCentreToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ personne });
        comp.ngOnInit();

        expect(centreService.query).toHaveBeenCalled();
        expect(centreService.addCentreToCollectionIfMissing).toHaveBeenCalledWith(centreCollection, ...additionalCentres);
        expect(comp.centresSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const personne: IPersonne = { id: 456 };
        const centre: ICentre = { id: 37560 };
        personne.centre = centre;

        activatedRoute.data = of({ personne });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(personne));
        expect(comp.centresSharedCollection).toContain(centre);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const personne = { id: 123 };
        spyOn(personneService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ personne });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: personne }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(personneService.update).toHaveBeenCalledWith(personne);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const personne = new Personne();
        spyOn(personneService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ personne });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: personne }));
        saveSubject.complete();

        // THEN
        expect(personneService.create).toHaveBeenCalledWith(personne);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const personne = { id: 123 };
        spyOn(personneService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ personne });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(personneService.update).toHaveBeenCalledWith(personne);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCentreById', () => {
        it('Should return tracked Centre primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCentreById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});

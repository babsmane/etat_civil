import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IPersonne, getPersonneIdentifier } from '../personne.model';

export type EntityResponseType = HttpResponse<IPersonne>;
export type EntityArrayResponseType = HttpResponse<IPersonne[]>;

@Injectable({ providedIn: 'root' })
export class PersonneService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/personnes');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/personnes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(personne: IPersonne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personne);
    return this.http
      .post<IPersonne>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(personne: IPersonne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personne);
    return this.http
      .put<IPersonne>(`${this.resourceUrl}/${getPersonneIdentifier(personne) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(personne: IPersonne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(personne);
    return this.http
      .patch<IPersonne>(`${this.resourceUrl}/${getPersonneIdentifier(personne) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPersonne>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPersonne[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPersonne[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addPersonneToCollectionIfMissing(personneCollection: IPersonne[], ...personnesToCheck: (IPersonne | null | undefined)[]): IPersonne[] {
    const personnes: IPersonne[] = personnesToCheck.filter(isPresent);
    if (personnes.length > 0) {
      const personneCollectionIdentifiers = personneCollection.map(personneItem => getPersonneIdentifier(personneItem)!);
      const personnesToAdd = personnes.filter(personneItem => {
        const personneIdentifier = getPersonneIdentifier(personneItem);
        if (personneIdentifier == null || personneCollectionIdentifiers.includes(personneIdentifier)) {
          return false;
        }
        personneCollectionIdentifiers.push(personneIdentifier);
        return true;
      });
      return [...personnesToAdd, ...personneCollection];
    }
    return personneCollection;
  }

  protected convertDateFromClient(personne: IPersonne): IPersonne {
    return Object.assign({}, personne, {
      dateOfBirthday: personne.dateOfBirthday?.isValid() ? personne.dateOfBirthday.toJSON() : undefined,
      hourOfBithday: personne.hourOfBithday?.isValid() ? personne.hourOfBithday.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirthday = res.body.dateOfBirthday ? dayjs(res.body.dateOfBirthday) : undefined;
      res.body.hourOfBithday = res.body.hourOfBithday ? dayjs(res.body.hourOfBithday) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((personne: IPersonne) => {
        personne.dateOfBirthday = personne.dateOfBirthday ? dayjs(personne.dateOfBirthday) : undefined;
        personne.hourOfBithday = personne.hourOfBithday ? dayjs(personne.hourOfBithday) : undefined;
      });
    }
    return res;
  }
}

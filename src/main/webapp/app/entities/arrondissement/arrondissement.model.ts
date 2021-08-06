import { ICentre } from 'app/entities/centre/centre.model';
import { ICommune } from 'app/entities/commune/commune.model';

export interface IArrondissement {
  id?: number;
  arrondissementCode?: number | null;
  arrondissementName?: string;
  centres?: ICentre[] | null;
  commune?: ICommune | null;
}

export class Arrondissement implements IArrondissement {
  constructor(
    public id?: number,
    public arrondissementCode?: number | null,
    public arrondissementName?: string,
    public centres?: ICentre[] | null,
    public commune?: ICommune | null
  ) {}
}

export function getArrondissementIdentifier(arrondissement: IArrondissement): number | undefined {
  return arrondissement.id;
}

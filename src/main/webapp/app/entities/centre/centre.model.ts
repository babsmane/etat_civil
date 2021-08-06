import { IPersonne } from 'app/entities/personne/personne.model';
import { IArrondissement } from 'app/entities/arrondissement/arrondissement.model';

export interface ICentre {
  id?: number;
  centreName?: string | null;
  centreChief?: string | null;
  personnes?: IPersonne[] | null;
  arrondissement?: IArrondissement | null;
}

export class Centre implements ICentre {
  constructor(
    public id?: number,
    public centreName?: string | null,
    public centreChief?: string | null,
    public personnes?: IPersonne[] | null,
    public arrondissement?: IArrondissement | null
  ) {}
}

export function getCentreIdentifier(centre: ICentre): number | undefined {
  return centre.id;
}

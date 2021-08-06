import { IArrondissement } from 'app/entities/arrondissement/arrondissement.model';
import { IDepartment } from 'app/entities/department/department.model';

export interface ICommune {
  id?: number;
  communeCode?: number | null;
  communeName?: string | null;
  arrondissements?: IArrondissement[] | null;
  department?: IDepartment | null;
}

export class Commune implements ICommune {
  constructor(
    public id?: number,
    public communeCode?: number | null,
    public communeName?: string | null,
    public arrondissements?: IArrondissement[] | null,
    public department?: IDepartment | null
  ) {}
}

export function getCommuneIdentifier(commune: ICommune): number | undefined {
  return commune.id;
}

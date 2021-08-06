import * as dayjs from 'dayjs';
import { ICentre } from 'app/entities/centre/centre.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';

export interface IPersonne {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  fatherName?: string | null;
  sexe?: Sexe | null;
  motherFirstName?: string | null;
  motherLastName?: string | null;
  dateOfBirthday?: dayjs.Dayjs | null;
  hourOfBithday?: dayjs.Dayjs | null;
  numberRegister?: number | null;
  centre?: ICentre | null;
}

export class Personne implements IPersonne {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public fatherName?: string | null,
    public sexe?: Sexe | null,
    public motherFirstName?: string | null,
    public motherLastName?: string | null,
    public dateOfBirthday?: dayjs.Dayjs | null,
    public hourOfBithday?: dayjs.Dayjs | null,
    public numberRegister?: number | null,
    public centre?: ICentre | null
  ) {}
}

export function getPersonneIdentifier(personne: IPersonne): number | undefined {
  return personne.id;
}

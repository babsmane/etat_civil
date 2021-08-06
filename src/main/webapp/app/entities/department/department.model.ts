import { ICommune } from 'app/entities/commune/commune.model';
import { IRegion } from 'app/entities/region/region.model';

export interface IDepartment {
  id?: number;
  departmentCode?: number | null;
  departmentName?: string;
  communes?: ICommune[] | null;
  region?: IRegion | null;
}

export class Department implements IDepartment {
  constructor(
    public id?: number,
    public departmentCode?: number | null,
    public departmentName?: string,
    public communes?: ICommune[] | null,
    public region?: IRegion | null
  ) {}
}

export function getDepartmentIdentifier(department: IDepartment): number | undefined {
  return department.id;
}

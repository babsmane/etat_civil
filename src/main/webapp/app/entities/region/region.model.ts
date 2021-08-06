import { IDepartment } from 'app/entities/department/department.model';

export interface IRegion {
  id?: number;
  regionCode?: number | null;
  regionName?: string;
  departments?: IDepartment[] | null;
}

export class Region implements IRegion {
  constructor(
    public id?: number,
    public regionCode?: number | null,
    public regionName?: string,
    public departments?: IDepartment[] | null
  ) {}
}

export function getRegionIdentifier(region: IRegion): number | undefined {
  return region.id;
}

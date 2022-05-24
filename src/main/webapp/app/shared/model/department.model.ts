import { ILocation } from '@/shared/model/location.model';
import { IEmployee } from '@/shared/model/employee.model';

export interface IDepartment {
  id?: string;
  departmentName?: string;
  location?: ILocation | null;
  employees?: IEmployee[] | null;
}

export class Department implements IDepartment {
  constructor(
    public id?: string,
    public departmentName?: string,
    public location?: ILocation | null,
    public employees?: IEmployee[] | null
  ) {}
}

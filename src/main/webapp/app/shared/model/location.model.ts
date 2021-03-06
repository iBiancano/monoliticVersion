import { ICountry } from '@/shared/model/country.model';

export interface ILocation {
  id?: string;
  streetAddress?: string | null;
  postalCode?: string | null;
  city?: string | null;
  stateProvince?: string | null;
  country?: ICountry | null;
}

export class Location implements ILocation {
  constructor(
    public id?: string,
    public streetAddress?: string | null,
    public postalCode?: string | null,
    public city?: string | null,
    public stateProvince?: string | null,
    public country?: ICountry | null
  ) {}
}

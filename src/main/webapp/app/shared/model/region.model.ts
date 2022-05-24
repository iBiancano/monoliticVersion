export interface IRegion {
  id?: string;
  regionName?: string | null;
}

export class Region implements IRegion {
  constructor(public id?: string, public regionName?: string | null) {}
}

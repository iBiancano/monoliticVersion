export interface IUnidad {
  id?: string;
  marca?: string | null;
  modelo?: string | null;
  matricula?: string | null;
  color?: string | null;
  numSerie?: string | null;
  generacion?: string | null;
}

export class Unidad implements IUnidad {
  constructor(
    public id?: string,
    public marca?: string | null,
    public modelo?: string | null,
    public matricula?: string | null,
    public color?: string | null,
    public numSerie?: string | null,
    public generacion?: string | null
  ) {}
}

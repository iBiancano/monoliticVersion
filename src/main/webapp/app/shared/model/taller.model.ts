import { IUnidadServicio } from '@/shared/model/unidad-servicio.model';

export interface ITaller {
  id?: string;
  marca?: string | null;
  modelo?: string | null;
  matricula?: string | null;
  color?: string | null;
  numSerie?: string | null;
  generacion?: string | null;
  unidadservicio?: IUnidadServicio | null;
}

export class Taller implements ITaller {
  constructor(
    public id?: string,
    public marca?: string | null,
    public modelo?: string | null,
    public matricula?: string | null,
    public color?: string | null,
    public numSerie?: string | null,
    public generacion?: string | null,
    public unidadservicio?: IUnidadServicio | null
  ) {}
}

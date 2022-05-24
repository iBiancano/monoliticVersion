import { IUnidad } from '@/shared/model/unidad.model';
import { IServicio } from '@/shared/model/servicio.model';

export interface IUnidadServicio {
  id?: string;
  fecha?: string | null;
  unidad?: IUnidad | null;
  servicio?: IServicio | null;
}

export class UnidadServicio implements IUnidadServicio {
  constructor(public id?: string, public fecha?: string | null, public unidad?: IUnidad | null, public servicio?: IServicio | null) {}
}

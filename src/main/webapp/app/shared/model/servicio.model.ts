export interface IServicio {
  id?: string;
  nombre?: string | null;
  duracion?: string | null;
  desc?: string | null;
}

export class Servicio implements IServicio {
  constructor(public id?: string, public nombre?: string | null, public duracion?: string | null, public desc?: string | null) {}
}

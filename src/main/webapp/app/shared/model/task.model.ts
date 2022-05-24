import { IJob } from '@/shared/model/job.model';

export interface ITask {
  id?: string;
  title?: string | null;
  description?: string | null;
  jobs?: IJob[] | null;
}

export class Task implements ITask {
  constructor(public id?: string, public title?: string | null, public description?: string | null, public jobs?: IJob[] | null) {}
}

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IServicio } from '@/shared/model/servicio.model';

import ServicioService from './servicio.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Servicio extends Vue {
  @Inject('servicioService') private servicioService: () => ServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public servicios: IServicio[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllServicios();
  }

  public clear(): void {
    this.retrieveAllServicios();
  }

  public retrieveAllServicios(): void {
    this.isFetching = true;
    this.servicioService()
      .retrieve()
      .then(
        res => {
          this.servicios = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IServicio): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeServicio(): void {
    this.servicioService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('pruebaMonoliticaApp.servicio.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllServicios();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}

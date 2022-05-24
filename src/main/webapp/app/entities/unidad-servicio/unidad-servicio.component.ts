import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IUnidadServicio } from '@/shared/model/unidad-servicio.model';

import UnidadServicioService from './unidad-servicio.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class UnidadServicio extends Vue {
  @Inject('unidadServicioService') private unidadServicioService: () => UnidadServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public unidadServicios: IUnidadServicio[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllUnidadServicios();
  }

  public clear(): void {
    this.retrieveAllUnidadServicios();
  }

  public retrieveAllUnidadServicios(): void {
    this.isFetching = true;
    this.unidadServicioService()
      .retrieve()
      .then(
        res => {
          this.unidadServicios = res.data;
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

  public prepareRemove(instance: IUnidadServicio): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeUnidadServicio(): void {
    this.unidadServicioService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('pruebaMonoliticaApp.unidadServicio.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllUnidadServicios();
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

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IUnidad } from '@/shared/model/unidad.model';

import UnidadService from './unidad.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Unidad extends Vue {
  @Inject('unidadService') private unidadService: () => UnidadService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public unidads: IUnidad[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllUnidads();
  }

  public clear(): void {
    this.retrieveAllUnidads();
  }

  public retrieveAllUnidads(): void {
    this.isFetching = true;
    this.unidadService()
      .retrieve()
      .then(
        res => {
          this.unidads = res.data;
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

  public prepareRemove(instance: IUnidad): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeUnidad(): void {
    this.unidadService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('pruebaMonoliticaApp.unidad.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllUnidads();
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

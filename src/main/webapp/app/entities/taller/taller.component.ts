import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITaller } from '@/shared/model/taller.model';

import TallerService from './taller.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Taller extends Vue {
  @Inject('tallerService') private tallerService: () => TallerService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public tallers: ITaller[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTallers();
  }

  public clear(): void {
    this.retrieveAllTallers();
  }

  public retrieveAllTallers(): void {
    this.isFetching = true;
    this.tallerService()
      .retrieve()
      .then(
        res => {
          this.tallers = res.data;
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

  public prepareRemove(instance: ITaller): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTaller(): void {
    this.tallerService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('pruebaMonoliticaApp.taller.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTallers();
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

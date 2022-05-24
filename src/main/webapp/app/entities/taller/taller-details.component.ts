import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITaller } from '@/shared/model/taller.model';
import TallerService from './taller.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TallerDetails extends Vue {
  @Inject('tallerService') private tallerService: () => TallerService;
  @Inject('alertService') private alertService: () => AlertService;

  public taller: ITaller = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tallerId) {
        vm.retrieveTaller(to.params.tallerId);
      }
    });
  }

  public retrieveTaller(tallerId) {
    this.tallerService()
      .find(tallerId)
      .then(res => {
        this.taller = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

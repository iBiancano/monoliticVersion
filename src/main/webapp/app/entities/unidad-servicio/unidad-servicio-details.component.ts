import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUnidadServicio } from '@/shared/model/unidad-servicio.model';
import UnidadServicioService from './unidad-servicio.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class UnidadServicioDetails extends Vue {
  @Inject('unidadServicioService') private unidadServicioService: () => UnidadServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public unidadServicio: IUnidadServicio = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.unidadServicioId) {
        vm.retrieveUnidadServicio(to.params.unidadServicioId);
      }
    });
  }

  public retrieveUnidadServicio(unidadServicioId) {
    this.unidadServicioService()
      .find(unidadServicioId)
      .then(res => {
        this.unidadServicio = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

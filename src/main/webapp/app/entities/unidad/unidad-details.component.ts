import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUnidad } from '@/shared/model/unidad.model';
import UnidadService from './unidad.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class UnidadDetails extends Vue {
  @Inject('unidadService') private unidadService: () => UnidadService;
  @Inject('alertService') private alertService: () => AlertService;

  public unidad: IUnidad = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.unidadId) {
        vm.retrieveUnidad(to.params.unidadId);
      }
    });
  }

  public retrieveUnidad(unidadId) {
    this.unidadService()
      .find(unidadId)
      .then(res => {
        this.unidad = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

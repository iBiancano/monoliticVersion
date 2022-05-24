import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IServicio, Servicio } from '@/shared/model/servicio.model';
import ServicioService from './servicio.service';

const validations: any = {
  servicio: {
    nombre: {},
    duracion: {},
    desc: {},
  },
};

@Component({
  validations,
})
export default class ServicioUpdate extends Vue {
  @Inject('servicioService') private servicioService: () => ServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public servicio: IServicio = new Servicio();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.servicioId) {
        vm.retrieveServicio(to.params.servicioId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.servicio.id) {
      this.servicioService()
        .update(this.servicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pruebaMonoliticaApp.servicio.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.servicioService()
        .create(this.servicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pruebaMonoliticaApp.servicio.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveServicio(servicioId): void {
    this.servicioService()
      .find(servicioId)
      .then(res => {
        this.servicio = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}

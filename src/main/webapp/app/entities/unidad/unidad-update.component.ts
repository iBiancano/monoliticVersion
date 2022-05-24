import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IUnidad, Unidad } from '@/shared/model/unidad.model';
import UnidadService from './unidad.service';

const validations: any = {
  unidad: {
    marca: {},
    modelo: {},
    matricula: {},
    color: {},
    numSerie: {},
    generacion: {},
  },
};

@Component({
  validations,
})
export default class UnidadUpdate extends Vue {
  @Inject('unidadService') private unidadService: () => UnidadService;
  @Inject('alertService') private alertService: () => AlertService;

  public unidad: IUnidad = new Unidad();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.unidadId) {
        vm.retrieveUnidad(to.params.unidadId);
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
    if (this.unidad.id) {
      this.unidadService()
        .update(this.unidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pruebaMonoliticaApp.unidad.updated', { param: param.id });
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
      this.unidadService()
        .create(this.unidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pruebaMonoliticaApp.unidad.created', { param: param.id });
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

  public retrieveUnidad(unidadId): void {
    this.unidadService()
      .find(unidadId)
      .then(res => {
        this.unidad = res;
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

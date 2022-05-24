import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import UnidadService from '@/entities/unidad/unidad.service';
import { IUnidad } from '@/shared/model/unidad.model';

import ServicioService from '@/entities/servicio/servicio.service';
import { IServicio } from '@/shared/model/servicio.model';

import { IUnidadServicio, UnidadServicio } from '@/shared/model/unidad-servicio.model';
import UnidadServicioService from './unidad-servicio.service';

const validations: any = {
  unidadServicio: {
    fecha: {},
  },
};

@Component({
  validations,
})
export default class UnidadServicioUpdate extends Vue {
  @Inject('unidadServicioService') private unidadServicioService: () => UnidadServicioService;
  @Inject('alertService') private alertService: () => AlertService;

  public unidadServicio: IUnidadServicio = new UnidadServicio();

  @Inject('unidadService') private unidadService: () => UnidadService;

  public unidads: IUnidad[] = [];

  @Inject('servicioService') private servicioService: () => ServicioService;

  public servicios: IServicio[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.unidadServicioId) {
        vm.retrieveUnidadServicio(to.params.unidadServicioId);
      }
      vm.initRelationships();
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
    if (this.unidadServicio.id) {
      this.unidadServicioService()
        .update(this.unidadServicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pruebaMonoliticaApp.unidadServicio.updated', { param: param.id });
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
      this.unidadServicioService()
        .create(this.unidadServicio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pruebaMonoliticaApp.unidadServicio.created', { param: param.id });
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

  public retrieveUnidadServicio(unidadServicioId): void {
    this.unidadServicioService()
      .find(unidadServicioId)
      .then(res => {
        this.unidadServicio = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.unidadService()
      .retrieve()
      .then(res => {
        this.unidads = res.data;
      });
    this.servicioService()
      .retrieve()
      .then(res => {
        this.servicios = res.data;
      });
  }
}

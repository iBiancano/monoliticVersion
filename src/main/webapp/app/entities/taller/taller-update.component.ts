import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import UnidadServicioService from '@/entities/unidad-servicio/unidad-servicio.service';
import { IUnidadServicio } from '@/shared/model/unidad-servicio.model';

import { ITaller, Taller } from '@/shared/model/taller.model';
import TallerService from './taller.service';

const validations: any = {
  taller: {
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
export default class TallerUpdate extends Vue {
  @Inject('tallerService') private tallerService: () => TallerService;
  @Inject('alertService') private alertService: () => AlertService;

  public taller: ITaller = new Taller();

  @Inject('unidadServicioService') private unidadServicioService: () => UnidadServicioService;

  public unidadServicios: IUnidadServicio[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tallerId) {
        vm.retrieveTaller(to.params.tallerId);
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
    if (this.taller.id) {
      this.tallerService()
        .update(this.taller)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pruebaMonoliticaApp.taller.updated', { param: param.id });
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
      this.tallerService()
        .create(this.taller)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('pruebaMonoliticaApp.taller.created', { param: param.id });
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

  public retrieveTaller(tallerId): void {
    this.tallerService()
      .find(tallerId)
      .then(res => {
        this.taller = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.unidadServicioService()
      .retrieve()
      .then(res => {
        this.unidadServicios = res.data;
      });
  }
}

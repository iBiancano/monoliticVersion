/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import UnidadServicioComponent from '@/entities/unidad-servicio/unidad-servicio.vue';
import UnidadServicioClass from '@/entities/unidad-servicio/unidad-servicio.component';
import UnidadServicioService from '@/entities/unidad-servicio/unidad-servicio.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('UnidadServicio Management Component', () => {
    let wrapper: Wrapper<UnidadServicioClass>;
    let comp: UnidadServicioClass;
    let unidadServicioServiceStub: SinonStubbedInstance<UnidadServicioService>;

    beforeEach(() => {
      unidadServicioServiceStub = sinon.createStubInstance<UnidadServicioService>(UnidadServicioService);
      unidadServicioServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UnidadServicioClass>(UnidadServicioComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          unidadServicioService: () => unidadServicioServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      unidadServicioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllUnidadServicios();
      await comp.$nextTick();

      // THEN
      expect(unidadServicioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.unidadServicios[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      unidadServicioServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(unidadServicioServiceStub.retrieve.callCount).toEqual(1);

      comp.removeUnidadServicio();
      await comp.$nextTick();

      // THEN
      expect(unidadServicioServiceStub.delete.called).toBeTruthy();
      expect(unidadServicioServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

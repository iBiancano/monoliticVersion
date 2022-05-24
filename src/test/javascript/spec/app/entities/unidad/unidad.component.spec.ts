/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import UnidadComponent from '@/entities/unidad/unidad.vue';
import UnidadClass from '@/entities/unidad/unidad.component';
import UnidadService from '@/entities/unidad/unidad.service';
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
  describe('Unidad Management Component', () => {
    let wrapper: Wrapper<UnidadClass>;
    let comp: UnidadClass;
    let unidadServiceStub: SinonStubbedInstance<UnidadService>;

    beforeEach(() => {
      unidadServiceStub = sinon.createStubInstance<UnidadService>(UnidadService);
      unidadServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UnidadClass>(UnidadComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          unidadService: () => unidadServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      unidadServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllUnidads();
      await comp.$nextTick();

      // THEN
      expect(unidadServiceStub.retrieve.called).toBeTruthy();
      expect(comp.unidads[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      unidadServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(unidadServiceStub.retrieve.callCount).toEqual(1);

      comp.removeUnidad();
      await comp.$nextTick();

      // THEN
      expect(unidadServiceStub.delete.called).toBeTruthy();
      expect(unidadServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

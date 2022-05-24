/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UnidadServicioDetailComponent from '@/entities/unidad-servicio/unidad-servicio-details.vue';
import UnidadServicioClass from '@/entities/unidad-servicio/unidad-servicio-details.component';
import UnidadServicioService from '@/entities/unidad-servicio/unidad-servicio.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('UnidadServicio Management Detail Component', () => {
    let wrapper: Wrapper<UnidadServicioClass>;
    let comp: UnidadServicioClass;
    let unidadServicioServiceStub: SinonStubbedInstance<UnidadServicioService>;

    beforeEach(() => {
      unidadServicioServiceStub = sinon.createStubInstance<UnidadServicioService>(UnidadServicioService);

      wrapper = shallowMount<UnidadServicioClass>(UnidadServicioDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { unidadServicioService: () => unidadServicioServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUnidadServicio = { id: 'ABC' };
        unidadServicioServiceStub.find.resolves(foundUnidadServicio);

        // WHEN
        comp.retrieveUnidadServicio('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.unidadServicio).toBe(foundUnidadServicio);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUnidadServicio = { id: 'ABC' };
        unidadServicioServiceStub.find.resolves(foundUnidadServicio);

        // WHEN
        comp.beforeRouteEnter({ params: { unidadServicioId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.unidadServicio).toBe(foundUnidadServicio);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});

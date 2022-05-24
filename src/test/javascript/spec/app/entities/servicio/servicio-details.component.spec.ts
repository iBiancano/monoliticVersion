/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ServicioDetailComponent from '@/entities/servicio/servicio-details.vue';
import ServicioClass from '@/entities/servicio/servicio-details.component';
import ServicioService from '@/entities/servicio/servicio.service';
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
  describe('Servicio Management Detail Component', () => {
    let wrapper: Wrapper<ServicioClass>;
    let comp: ServicioClass;
    let servicioServiceStub: SinonStubbedInstance<ServicioService>;

    beforeEach(() => {
      servicioServiceStub = sinon.createStubInstance<ServicioService>(ServicioService);

      wrapper = shallowMount<ServicioClass>(ServicioDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { servicioService: () => servicioServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundServicio = { id: 'ABC' };
        servicioServiceStub.find.resolves(foundServicio);

        // WHEN
        comp.retrieveServicio('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.servicio).toBe(foundServicio);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundServicio = { id: 'ABC' };
        servicioServiceStub.find.resolves(foundServicio);

        // WHEN
        comp.beforeRouteEnter({ params: { servicioId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.servicio).toBe(foundServicio);
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

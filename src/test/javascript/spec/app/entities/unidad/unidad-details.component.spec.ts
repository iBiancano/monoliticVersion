/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UnidadDetailComponent from '@/entities/unidad/unidad-details.vue';
import UnidadClass from '@/entities/unidad/unidad-details.component';
import UnidadService from '@/entities/unidad/unidad.service';
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
  describe('Unidad Management Detail Component', () => {
    let wrapper: Wrapper<UnidadClass>;
    let comp: UnidadClass;
    let unidadServiceStub: SinonStubbedInstance<UnidadService>;

    beforeEach(() => {
      unidadServiceStub = sinon.createStubInstance<UnidadService>(UnidadService);

      wrapper = shallowMount<UnidadClass>(UnidadDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { unidadService: () => unidadServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUnidad = { id: 'ABC' };
        unidadServiceStub.find.resolves(foundUnidad);

        // WHEN
        comp.retrieveUnidad('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.unidad).toBe(foundUnidad);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUnidad = { id: 'ABC' };
        unidadServiceStub.find.resolves(foundUnidad);

        // WHEN
        comp.beforeRouteEnter({ params: { unidadId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.unidad).toBe(foundUnidad);
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

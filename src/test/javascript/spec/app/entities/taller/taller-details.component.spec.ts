/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TallerDetailComponent from '@/entities/taller/taller-details.vue';
import TallerClass from '@/entities/taller/taller-details.component';
import TallerService from '@/entities/taller/taller.service';
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
  describe('Taller Management Detail Component', () => {
    let wrapper: Wrapper<TallerClass>;
    let comp: TallerClass;
    let tallerServiceStub: SinonStubbedInstance<TallerService>;

    beforeEach(() => {
      tallerServiceStub = sinon.createStubInstance<TallerService>(TallerService);

      wrapper = shallowMount<TallerClass>(TallerDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { tallerService: () => tallerServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTaller = { id: 'ABC' };
        tallerServiceStub.find.resolves(foundTaller);

        // WHEN
        comp.retrieveTaller('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.taller).toBe(foundTaller);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTaller = { id: 'ABC' };
        tallerServiceStub.find.resolves(foundTaller);

        // WHEN
        comp.beforeRouteEnter({ params: { tallerId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.taller).toBe(foundTaller);
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

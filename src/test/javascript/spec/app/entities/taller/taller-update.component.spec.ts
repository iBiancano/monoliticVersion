/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TallerUpdateComponent from '@/entities/taller/taller-update.vue';
import TallerClass from '@/entities/taller/taller-update.component';
import TallerService from '@/entities/taller/taller.service';

import UnidadServicioService from '@/entities/unidad-servicio/unidad-servicio.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Taller Management Update Component', () => {
    let wrapper: Wrapper<TallerClass>;
    let comp: TallerClass;
    let tallerServiceStub: SinonStubbedInstance<TallerService>;

    beforeEach(() => {
      tallerServiceStub = sinon.createStubInstance<TallerService>(TallerService);

      wrapper = shallowMount<TallerClass>(TallerUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          tallerService: () => tallerServiceStub,
          alertService: () => new AlertService(),

          unidadServicioService: () =>
            sinon.createStubInstance<UnidadServicioService>(UnidadServicioService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 'ABC' };
        comp.taller = entity;
        tallerServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tallerServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.taller = entity;
        tallerServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tallerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTaller = { id: 'ABC' };
        tallerServiceStub.find.resolves(foundTaller);
        tallerServiceStub.retrieve.resolves([foundTaller]);

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

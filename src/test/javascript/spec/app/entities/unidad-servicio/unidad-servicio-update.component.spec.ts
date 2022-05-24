/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import UnidadServicioUpdateComponent from '@/entities/unidad-servicio/unidad-servicio-update.vue';
import UnidadServicioClass from '@/entities/unidad-servicio/unidad-servicio-update.component';
import UnidadServicioService from '@/entities/unidad-servicio/unidad-servicio.service';

import UnidadService from '@/entities/unidad/unidad.service';

import ServicioService from '@/entities/servicio/servicio.service';
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
  describe('UnidadServicio Management Update Component', () => {
    let wrapper: Wrapper<UnidadServicioClass>;
    let comp: UnidadServicioClass;
    let unidadServicioServiceStub: SinonStubbedInstance<UnidadServicioService>;

    beforeEach(() => {
      unidadServicioServiceStub = sinon.createStubInstance<UnidadServicioService>(UnidadServicioService);

      wrapper = shallowMount<UnidadServicioClass>(UnidadServicioUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          unidadServicioService: () => unidadServicioServiceStub,
          alertService: () => new AlertService(),

          unidadService: () =>
            sinon.createStubInstance<UnidadService>(UnidadService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          servicioService: () =>
            sinon.createStubInstance<ServicioService>(ServicioService, {
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
        comp.unidadServicio = entity;
        unidadServicioServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(unidadServicioServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.unidadServicio = entity;
        unidadServicioServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(unidadServicioServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUnidadServicio = { id: 'ABC' };
        unidadServicioServiceStub.find.resolves(foundUnidadServicio);
        unidadServicioServiceStub.retrieve.resolves([foundUnidadServicio]);

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

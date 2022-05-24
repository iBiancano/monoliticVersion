/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import UnidadUpdateComponent from '@/entities/unidad/unidad-update.vue';
import UnidadClass from '@/entities/unidad/unidad-update.component';
import UnidadService from '@/entities/unidad/unidad.service';

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
  describe('Unidad Management Update Component', () => {
    let wrapper: Wrapper<UnidadClass>;
    let comp: UnidadClass;
    let unidadServiceStub: SinonStubbedInstance<UnidadService>;

    beforeEach(() => {
      unidadServiceStub = sinon.createStubInstance<UnidadService>(UnidadService);

      wrapper = shallowMount<UnidadClass>(UnidadUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          unidadService: () => unidadServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 'ABC' };
        comp.unidad = entity;
        unidadServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(unidadServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.unidad = entity;
        unidadServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(unidadServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUnidad = { id: 'ABC' };
        unidadServiceStub.find.resolves(foundUnidad);
        unidadServiceStub.retrieve.resolves([foundUnidad]);

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

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TallerComponent from '@/entities/taller/taller.vue';
import TallerClass from '@/entities/taller/taller.component';
import TallerService from '@/entities/taller/taller.service';
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
  describe('Taller Management Component', () => {
    let wrapper: Wrapper<TallerClass>;
    let comp: TallerClass;
    let tallerServiceStub: SinonStubbedInstance<TallerService>;

    beforeEach(() => {
      tallerServiceStub = sinon.createStubInstance<TallerService>(TallerService);
      tallerServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TallerClass>(TallerComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          tallerService: () => tallerServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      tallerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllTallers();
      await comp.$nextTick();

      // THEN
      expect(tallerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tallers[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      tallerServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(tallerServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTaller();
      await comp.$nextTick();

      // THEN
      expect(tallerServiceStub.delete.called).toBeTruthy();
      expect(tallerServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

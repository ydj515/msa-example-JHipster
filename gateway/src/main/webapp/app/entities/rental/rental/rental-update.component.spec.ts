/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RentalUpdate from './rental-update.vue';
import RentalService from './rental.service';
import AlertService from '@/shared/alert/alert.service';

type RentalUpdateComponentType = InstanceType<typeof RentalUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rentalSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RentalUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Rental Management Update Component', () => {
    let comp: RentalUpdateComponentType;
    let rentalServiceStub: SinonStubbedInstance<RentalService>;

    beforeEach(() => {
      route = {};
      rentalServiceStub = sinon.createStubInstance<RentalService>(RentalService);
      rentalServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          rentalService: () => rentalServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(RentalUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rental = rentalSample;
        rentalServiceStub.update.resolves(rentalSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rentalServiceStub.update.calledWith(rentalSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rentalServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RentalUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rental = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rentalServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rentalServiceStub.find.resolves(rentalSample);
        rentalServiceStub.retrieve.resolves([rentalSample]);

        // WHEN
        route = {
          params: {
            rentalId: `${rentalSample.id}`,
          },
        };
        const wrapper = shallowMount(RentalUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rental).toMatchObject(rentalSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rentalServiceStub.find.resolves(rentalSample);
        const wrapper = shallowMount(RentalUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

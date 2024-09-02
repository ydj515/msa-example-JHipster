/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RentedItemUpdate from './rented-item-update.vue';
import RentedItemService from './rented-item.service';
import AlertService from '@/shared/alert/alert.service';

import RentalService from '@/entities/rental/rental/rental.service';

type RentedItemUpdateComponentType = InstanceType<typeof RentedItemUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rentedItemSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RentedItemUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RentedItem Management Update Component', () => {
    let comp: RentedItemUpdateComponentType;
    let rentedItemServiceStub: SinonStubbedInstance<RentedItemService>;

    beforeEach(() => {
      route = {};
      rentedItemServiceStub = sinon.createStubInstance<RentedItemService>(RentedItemService);
      rentedItemServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          rentedItemService: () => rentedItemServiceStub,
          rentalService: () =>
            sinon.createStubInstance<RentalService>(RentalService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(RentedItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rentedItem = rentedItemSample;
        rentedItemServiceStub.update.resolves(rentedItemSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rentedItemServiceStub.update.calledWith(rentedItemSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rentedItemServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RentedItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rentedItem = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rentedItemServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rentedItemServiceStub.find.resolves(rentedItemSample);
        rentedItemServiceStub.retrieve.resolves([rentedItemSample]);

        // WHEN
        route = {
          params: {
            rentedItemId: `${rentedItemSample.id}`,
          },
        };
        const wrapper = shallowMount(RentedItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rentedItem).toMatchObject(rentedItemSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rentedItemServiceStub.find.resolves(rentedItemSample);
        const wrapper = shallowMount(RentedItemUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

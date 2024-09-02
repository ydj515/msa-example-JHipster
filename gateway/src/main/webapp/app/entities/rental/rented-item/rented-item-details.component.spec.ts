/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RentedItemDetails from './rented-item-details.vue';
import RentedItemService from './rented-item.service';
import AlertService from '@/shared/alert/alert.service';

type RentedItemDetailsComponentType = InstanceType<typeof RentedItemDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rentedItemSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RentedItem Management Detail Component', () => {
    let rentedItemServiceStub: SinonStubbedInstance<RentedItemService>;
    let mountOptions: MountingOptions<RentedItemDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rentedItemServiceStub = sinon.createStubInstance<RentedItemService>(RentedItemService);

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          rentedItemService: () => rentedItemServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rentedItemServiceStub.find.resolves(rentedItemSample);
        route = {
          params: {
            rentedItemId: `${123}`,
          },
        };
        const wrapper = shallowMount(RentedItemDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rentedItem).toMatchObject(rentedItemSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rentedItemServiceStub.find.resolves(rentedItemSample);
        const wrapper = shallowMount(RentedItemDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

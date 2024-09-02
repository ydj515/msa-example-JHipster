/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RentalDetails from './rental-details.vue';
import RentalService from './rental.service';
import AlertService from '@/shared/alert/alert.service';

type RentalDetailsComponentType = InstanceType<typeof RentalDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rentalSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Rental Management Detail Component', () => {
    let rentalServiceStub: SinonStubbedInstance<RentalService>;
    let mountOptions: MountingOptions<RentalDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      rentalServiceStub = sinon.createStubInstance<RentalService>(RentalService);

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
          rentalService: () => rentalServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rentalServiceStub.find.resolves(rentalSample);
        route = {
          params: {
            rentalId: `${123}`,
          },
        };
        const wrapper = shallowMount(RentalDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.rental).toMatchObject(rentalSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rentalServiceStub.find.resolves(rentalSample);
        const wrapper = shallowMount(RentalDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

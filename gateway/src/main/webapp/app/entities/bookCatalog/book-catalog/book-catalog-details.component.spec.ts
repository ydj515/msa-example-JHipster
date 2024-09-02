/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import BookCatalogDetails from './book-catalog-details.vue';
import BookCatalogService from './book-catalog.service';
import AlertService from '@/shared/alert/alert.service';

type BookCatalogDetailsComponentType = InstanceType<typeof BookCatalogDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const bookCatalogSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('BookCatalog Management Detail Component', () => {
    let bookCatalogServiceStub: SinonStubbedInstance<BookCatalogService>;
    let mountOptions: MountingOptions<BookCatalogDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      bookCatalogServiceStub = sinon.createStubInstance<BookCatalogService>(BookCatalogService);

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
          bookCatalogService: () => bookCatalogServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        bookCatalogServiceStub.find.resolves(bookCatalogSample);
        route = {
          params: {
            bookCatalogId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(BookCatalogDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.bookCatalog).toMatchObject(bookCatalogSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        bookCatalogServiceStub.find.resolves(bookCatalogSample);
        const wrapper = shallowMount(BookCatalogDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

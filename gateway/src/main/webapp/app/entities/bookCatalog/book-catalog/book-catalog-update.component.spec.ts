/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import BookCatalogUpdate from './book-catalog-update.vue';
import BookCatalogService from './book-catalog.service';
import AlertService from '@/shared/alert/alert.service';

type BookCatalogUpdateComponentType = InstanceType<typeof BookCatalogUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const bookCatalogSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<BookCatalogUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('BookCatalog Management Update Component', () => {
    let comp: BookCatalogUpdateComponentType;
    let bookCatalogServiceStub: SinonStubbedInstance<BookCatalogService>;

    beforeEach(() => {
      route = {};
      bookCatalogServiceStub = sinon.createStubInstance<BookCatalogService>(BookCatalogService);
      bookCatalogServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          bookCatalogService: () => bookCatalogServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(BookCatalogUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.bookCatalog = bookCatalogSample;
        bookCatalogServiceStub.update.resolves(bookCatalogSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(bookCatalogServiceStub.update.calledWith(bookCatalogSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        bookCatalogServiceStub.create.resolves(entity);
        const wrapper = shallowMount(BookCatalogUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.bookCatalog = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(bookCatalogServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        bookCatalogServiceStub.find.resolves(bookCatalogSample);
        bookCatalogServiceStub.retrieve.resolves([bookCatalogSample]);

        // WHEN
        route = {
          params: {
            bookCatalogId: `${bookCatalogSample.id}`,
          },
        };
        const wrapper = shallowMount(BookCatalogUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.bookCatalog).toMatchObject(bookCatalogSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        bookCatalogServiceStub.find.resolves(bookCatalogSample);
        const wrapper = shallowMount(BookCatalogUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

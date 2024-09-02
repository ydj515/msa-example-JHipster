/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import BookCatalog from './book-catalog.vue';
import BookCatalogService from './book-catalog.service';
import AlertService from '@/shared/alert/alert.service';

type BookCatalogComponentType = InstanceType<typeof BookCatalog>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('BookCatalog Management Component', () => {
    let bookCatalogServiceStub: SinonStubbedInstance<BookCatalogService>;
    let mountOptions: MountingOptions<BookCatalogComponentType>['global'];

    beforeEach(() => {
      bookCatalogServiceStub = sinon.createStubInstance<BookCatalogService>(BookCatalogService);
      bookCatalogServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          jhiItemCount: true,
          bPagination: true,
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'jhi-sort-indicator': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          bookCatalogService: () => bookCatalogServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        bookCatalogServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(BookCatalog, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(bookCatalogServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.bookCatalogs[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(BookCatalog, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(bookCatalogServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: BookCatalogComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(BookCatalog, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        bookCatalogServiceStub.retrieve.reset();
        bookCatalogServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        bookCatalogServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(bookCatalogServiceStub.retrieve.called).toBeTruthy();
        expect(comp.bookCatalogs[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(bookCatalogServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        bookCatalogServiceStub.retrieve.reset();
        bookCatalogServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(bookCatalogServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.bookCatalogs[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(bookCatalogServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        bookCatalogServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeBookCatalog();
        await comp.$nextTick(); // clear components

        // THEN
        expect(bookCatalogServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(bookCatalogServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
